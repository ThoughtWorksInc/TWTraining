#!/bin/bash
## This file sets up a simple SVN server that can be used for training. The "server" can be a simple laptop with sufficient memory (6-8 GB) and hard-drive space (100-200 GB). 

## CHANGE the following line to reflect where you want the svn repository to exist on this computer:
svnroot=/Users/Thoughtworker/temp/svnroot

## CHANGE the following line to reflect the fully-qualified path of the videoworld folder (within the ADP material, within the TWTraining folder) on this computer:
source=/Projects/StudiosTraining/github/TWTraining/Agile\ Development\ Practices/Master/Code/Java/videoworld/

########################################################
## You SHOULD NOT NEED TO CHANGE the following commands.
## If you think any of them are not working as expected, run the script in debug mode "bash -x ./svnsetup.sh" to identify any problems.
########################################################

##It simply creates a file-url from the "svnroot" specified above
svnrooturl=file://$svnroot

## This creates a subversion repository at the location specified by "svnroot"
svnadmin create $svnroot

## This creates a standard svn repo with the "trunk, branches, tags" layout.
svn mkdir $svnrooturl/$1/trunk $svnrooturl/$1/branches $svnrooturl/$1/tags -m "New Project $1" --parents

## This loop creates projects for "instructor1" and "instructor2"
for i in 1 2 
do
  svn import -m "[instructor] Initial import of project" "$source" $svnrooturl/$1/trunk/instructor$i
done

## This loop creates projects for 9 pairs of developers, "pair1" through "pair9"
for i in {1..9} 
do
 svn import -m "[instructor] Initial import of project" "$source" $svnrooturl/$1/trunk/pair$i
done

## This copies the password file for the two instructors and 9 dev-pairs
cp -f passwd $svnroot/conf

## This copies the svn config file so that the above password file is used for authentication
cp -f svnserve.conf $svnroot/conf

## This starts the svn server for the repository just created
svnserve -d -r $svnroot

## To check the repo has been created try this command:
# svn -R --depth immediates info svn://ambareen-pc/trunk/
## To check out files, you need one of the two commands below:
## 1. If using git and git-svn (highly recommended):
# git svn clone svn://pair1@ambareen-pc/trunk/pair1 videoworld
## 2. If using straight-up svn:
#svn co svn://pair1@ambareen-pc/trunk/pair1 videoworld
## To verify you can commit files, change the README file and then execute:
#svn ci -m "test" --username pair1 --password password 
