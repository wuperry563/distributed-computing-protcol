#!/bin/bash


# Change this to your netid
netid=psw101020

#
# Root directory of your project
PROJDIR=~/dev/CS6378/Project1
echo $PROJDIR
echo $netid
#
# This assumes your config file is named "config.txt"
# and is located in your project directory
#
#CONFIG=$PROJDIR/NOT-THIS-YEARS-CONFIG.txt
#CONFIG=config.txt (put it as command line argument)
#
# Directory your java classes are in
#
#BINDIR=$PROJDIR/bin

#
# Your main project class
#
PROG=Project1
echo $PROG
echo $1

n=0

cat $1 | sed -e "s/#.*//" | sed -e "/^\s*$/d" |
(
    read i
    echo $i
    
    k=$( echo $i | awk '{ print $1 }' )    
	echo $k
    
    echo "k=" $k
    while read line 
    do
	echo DO
        if [ $n -lt $k ]
	then
	host=$( echo $line | awk '{ print $2 }' )
	echo host=$host
	
	nodeId=$( echo $line | awk '{ print $3 }' )
	echo $netid "Running on" $host "with" $nodeId $n

        ssh -oStrictHostKeyChecking=no -l "$netid" "$host" "cd ; cd $PROJDIR; java Start $n $1 " &
	fi
        n=$(( n + 1 ))
    done
   
)



