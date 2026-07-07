| Tool            | Answers the Question                          |
| --------------- | --------------------------------------------- |
| **Thread Dump** | **What are my threads doing right now?**      |
| **Heap Dump**   | **What objects are occupying memory?**        |
| **GC Logs**     | **How is the JVM managing memory over time?** |

## Thread Dump

- A thread dump is a snapshot of all JVM threads at a particular instant.
- It shows: Running threads, Waiting threads, Blocked threads, Deadlocks , Stack traces, Locks held by threads
- Helps detect Deadlock, High CPU (infinine loop), Slow Requests (blocked threads)

## Heap Dump

- A heap dump is a snapshot of every object currently in JVM memory.
- It help debug: Memory Leak, OutOfMemoryError

## GC Logs

- GC logs tell you how garbage collection behaves over time.

A thread dump, heap dump, and GC logs serve different diagnostic purposes. A thread dump captures the state of all JVM threads at a specific moment, including their stack traces and lock states, making it useful for diagnosing deadlocks, blocked threads, high CPU usage, or hung requests. A heap dump captures all live objects in JVM memory, allowing us to analyze memory leaks, large object graphs, and OutOfMemoryError issues using tools like Eclipse MAT or VisualVM. GC logs, on the other hand, record garbage collection events over time, including heap usage before and after collection, pause durations, and collection frequency. They are primarily used to tune GC performance and identify excessive garbage collection or long pause times. In production, if an application is hanging, I'd start with a thread dump; if memory keeps increasing, I'd analyze a heap dump; and if latency spikes coincide with garbage collection, I'd examine GC logs.