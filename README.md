FKS
===

# perfect-hash
We say a hash function is perfect for S if all lookups involve O(1) work.

# FKS (Fredman, Komlos and Szemeredi)
* Construct static hash table with no collisions in expected O(n) time,
O(n) worst-casespace, and O(1) worst-case query time.
* Requires a weak universal family H
* Easytoimplement

##FKS use 2 levels of hashing:
* first level: hash n keys into m slots.
* second level: for each slot build a new array with size n^2 and hash with no collisions (p>0.5)

Universal Hash used:
h(k) = ((ak + b) mod p) mod m