
# Rece War

This project is a game designed in Java.


## Improvements
Our first upgrade was the idea of dividing the project in two frames, one for the Menu and the other for the battle.

Then we decided to change the stats values for the users and the weapons, you can find all the changes in the
[Rebalance Table](https://docs.google.com/spreadsheets/d/1ufRzPQU2TLZsvlG6Mp-NhqoY8Vm5OGwJ5wNneyL3aVI/edit?usp=sharing).

Other improvements are the implementation of bosses, items and a final boss.

To have a lower number of rounds we change the conditional to dodge attacks. 
In the begining was:
```java
if(defender.getTotalAgility() < dodgeChance) { 
  // Into this conditional we calculate the damages
}
``` 
And we changed it to:
```java
if(defender.getTotalAgility()/2 < dodgeChance) { 
  // Into this conditional we calculate the damages
}
``` 


## Installation

Install MySQL

Create a user with name 'root' and password '1234'

Then run the files RaceWar-schema.sql and RaceWar-populate.sql using the user 'root'.

The files are in the M02 directory.

With both files executed with the user 'root', now you can run the project in Eclipse.


## Authors

- GitHub - [@Pablo](https://github.com/Bombonas)
- GitHub - [@Alex](https://github.com/LeoAlendro171)
- GitHub - [@David](https://github.com/dsanchezmatilla)


