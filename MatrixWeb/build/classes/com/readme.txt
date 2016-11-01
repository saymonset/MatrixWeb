CS526 Project #1: Access Control Matrices

In the lecture, we have talked about the access control. Each subject will be granted different access privileges over different objects. We assume there are 7 different privileges:

r for read;
w for write;
x for execute;
E for none;
gr for grant-read: subject can read and grant other subjects an r right for that object (but not a gr);
gw for grant-write: subject can write and grant other subjects a w right for that object (but not a gw);
gx for grant-execute: subject can execute and grant other subjects an x right for that object (but not a gw);
In this project, you are required to implement a software to determine access privilege automatically. The software will read from two files as input. The first file represents the access control matrix. And the second one includes many lines of queries (one query per line). The output of this software should be the answers to all those queries from the second file.

Please check the sample files blow and make sure your software work with them:

Access Matrix Queries Output

Access Control Matrices:

The access control matrix is similar to the one in Q4 of HW3:

Access Matrix

The space between columns are tabs.

Query:

The software should process a query as soon as it is read (no batch processing of queries whereby they're all read first, then all processed together). And please also attach time consumption for this query to the end of your output. And make sure the time consumption is not 0. For example, if it is 0.0 at second level, please have finer granularity until it is non-zero.

There are two types of queries:

1) Access Subject Object Right

For which your software need to determine whether there is a sequence of events that can cause Subject to carry out on Object an action of type Right (which could happen by acquiring Right, or indirectly by over-writing a program that can do Right in a manner controlled indirectly by Subject). If your answer is Yes, then you should produce a shortest such sequence of events (if there are many then any is OK as output, as long as there is no shorter one).

Example #1:

Query: Type1: Alice Program1 r

Output: NO; time: 0.3 ms

Example #2:

Query: Type1: David Program2 x

Output: YES: David Program1 x Program2 x; time 1.0 ms

2) Prevent Subject Object Right

For which your software need to determine a small set of modifications to the access control matrix that will make the answer to an Access query become No. The output for such a query is of the form S1 O1 R1 S2 O2 R2 .. Sn On Rn where n is the number of modifications, and each Si Oi Ri triplet means Change the matrix position at row Si and column Oi to become Ri (where Ri is different from the previous contents of that matrix entry). Again, a small n is preferred (but smallest cannot be assured without spending an inordinate amount of computing time).

Example:

Query: Type2: David Program2 x

Output: 4 3 w; time 3.0 s

Here we change "David Program1 w x" to "David Program1 w" and remove David execution privilege on Program1. Of course, there are some other options which have the same effect.

Grading policy:

1) Basic grades: 30pt

If your software could give correct answers to more than 30% queries for both types, you will receive the basic grades.

For example, we have 10 queries for each type. And you have 3 correct answers to both type 1 query and type 2 query, you will receive 30pt. But if you have 4 correct answers to type 1 query but only 2 correct answers to type 2, you only receive 15pt.

2) Correctness grades: 30pt

If your software could give correct answers to all queries, you will receive another 30 pt.

At this moment, we do not consider if you can give the optimized solutions. But you must guarantee your software produces the output within reasonable time. For example, if most of studentsâ software produce correct outputs within 1 min, more than 3 mins is not an acceptable time consumption.

If your software fails to have correct answers to all queries, you will receive partial grades based on you correct rate.

3) Optimization grades: 40pt

If your software could give the shortest correct answers to all queries, you will receive this 40 pt.

For query type 1, you must always give the shortest one. For query type 2, you only need have a small n (maybe not the minimum one).

If your software fails to have correct answers to all queries or not all of your answers are optimized, you will receive partial grades based on the output.

Requirements:

Please implement your software with Java or Python. And include a README which have detailed instructions on usage of your software. And group work is not allowed. Please finish the project independently.

Please also have a pdf file which includes the detailed design (your algorithm) about your software.

Due time: 11:59pm Oct 30, 2016.

Submission:

Please create a zip file includes the following things:

README.txt;
Project1-ans.pdf;
Your software source codes and Makefile (if necessary for compiling);
And please name your compressed file as: your_purdue_user_name-project1.zip. Please mail your project to TA before the deadline with email title: CS526 Project 1 submission: your_purdue_user_name.
