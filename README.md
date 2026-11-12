# Hospital Concurrent Patient Management System

A multithreaded Java simulation of a hospital where doctors, nurses, laboratory
technicians and pharmacists work at the same time on shared patient records and a
shared medicine inventory. Built for the Java concurrency assignment (Problem 2, Group 11-15).

## The scenario

| Resource | Count |
|---|---|
| Registered patients | 100 |
| Doctors | 5 |
| Nurses | 10 |
| Laboratory technicians | 3 (sharing 2 lab machines) |
| Pharmacists | 2 |
| Medicine inventory | 50 doses |

## How to run

Requires JDK 17 or later.

```
javac *.java
java Main
```

To see just the summary, run `java Main | tail -8`. Expected result, every run:

```
Patients registered:      100
Consistent records:       100/100
Paracetamol left:         0
Patients given medicine:  50
Hospital closed
```

All 100 patients request one dose, but only 50 exist, so exactly 50 succeed and 50
are refused. The order of the log lines changes on every run (that is real
concurrency), but the final totals never do.

## How it works

```
Nurses (10) --LabRequest--> [labRequestQueue] --> Lab technicians (3) --LabResult--> [resultQueue] --> Doctors (5)
                                                       |
                                              Semaphore (2 machines)

Pharmacists (2) --> MedicineInventory (synchronized)
All staff        --> PatientRepository --> PatientRecord (one ReentrantLock each)
```

1. **Nurses** record a patient's vitals and submit a lab request to the request queue.
2. **Lab technicians** take requests from the queue, use a lab machine, and post a result to the result queue.
3. **Doctors** retrieve completed results, then add the result and a note to the patient's record.
4. **Pharmacists** dispense medicine from the shared inventory, which can never go negative.

## Where each concurrency mechanism is used

| Mechanism | Where | Why |
|---|---|---|
| `synchronized` | `MedicineInventory` | Check-then-update on stock must be one atomic step, so two pharmacists can never dispense the same dose |
| `ReentrantLock` | `PatientRecord` (one fair lock per record) | Several workers update the same patient; vitals, notes and results must stay consistent. Different patients never block each other |
| `ReentrantLock` | `PatientCounter` | Counts treated patients safely across pharmacists |
| `BlockingQueue` | Request queue and result queue | Thread-safe hand-off between nurses, technicians and doctors. `take()` waits when empty, `put()` waits when full, so nobody busy-waits |
| `Semaphore` | Lab machines (2 permits, fair) | 3 technicians share 2 machines, so at most 2 tests run at once |
| `ExecutorService` | `Main` (fixed pool of 20 threads) | Runs all 20 workers (10 + 3 + 5 + 2) as tasks instead of creating threads by hand |

## Design notes

- **Per-record locking.** Each `PatientRecord` has its own lock, so two doctors on different
  patients never wait for each other. Reads take the lock too, so no one sees a half-finished update.
- **Pool size matters.** Technicians and doctors spend most of their time blocked on queues,
  so the pool needs one thread for every worker (20). A smaller pool can hang the program.
- **Ordered shutdown.** Nurses finish, then one stop signal per technician goes into the request
  queue, then technicians finish, then one stop signal per doctor goes into the result queue.
  Stop signals sit behind real work, so no request is dropped.
- **Starvation prevention.** Queues are FIFO, and the semaphore and record locks are created
  as fair, so waiting threads are served in arrival order.
- **Machine released before posting results.** A technician frees the lab machine before
  putting the result on the queue, so a full result queue never holds a machine hostage.

## Project structure

| File | Role |
|---|---|
| `Main.java` | Builds the hospital, starts the pool, shuts down in order, checks consistency |
| `Patient.java` | Immutable patient identity |
| `PatientRecord.java` | Mutable record (vitals, notes, results) protected by a fair `ReentrantLock` |
| `PatientRepository.java` | Shared collection of all 100 records (`ConcurrentHashMap`) |
| `MedicineInventory.java` | Shared stock, thread-safe with `synchronized` |
| `PatientCounter.java` | Thread-safe count of treated patients |
| `LabRequest.java`, `LabResult.java` | Messages passed through the two queues |
| `Nurse.java`, `LabTech.java`, `Doctor.java`, `Pharmacist.java` | The worker threads (`Runnable`) |
| `RaceDemo.java` | Shows the inventory bug without synchronization (1761 doses from a stock of 1000) |
| `RecordDemo.java` | Stress test: 15 threads updating one record |

## Group members

- Name 1
- Name 2
