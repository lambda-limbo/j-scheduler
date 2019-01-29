# BEWARE, THE CODE IS HORRIBLE WRITTEN, NOW PROCEED. :poop:

# A Java scheduler

A simple scheduler implemented in Java as a university homework for the OS class.

# Roadmap

- [x] Implement a process
- [x] Implement a process table
- [x] Implement strategy for the scheduler
- [x] Implement an interface to create and kill processes
- [x] Implement an interface for viewing process that are executing


# The process

The process can be viewed as an abstraction of a running program on top of the
operating system (or it could be the operating system itself). Because of that
there are some properties we wish to have. A process must have the following
properties:

- The PID of the process
- The name of the process
- The time needed to complete the process
- The priority of the process (this should be implemented but not used on the scheduler yet)

# The process table

A simple PriorityQueue which orders elements (processes) with respect to its
priority. It also feeds the processor with process to process

# The processor

A class that represents a processor whose job is to take a process and process
it, it subtracts the time slice of the process being executed and makes the
interface and other process to be executed respecting to the time slice of the
processor.

# The scheduler strategy

It is a short-term scheduler (as we are dealing with a CPU) that has a process
table implemented as a `PriorityQueue<Process>` and feeds the processor with
process on top of the priority queue which it executes the process as specified
above. It might not look like a real scheduler at all. Beware.

# The process interface (creating and killing it)

A window that shows buttons to create and kill a process as well as the process
that is current being processed and the process queue which state what processes
are waiting to be executed.
