#!/bin/bash

echo Running in $SHELL

echo "$(tput setaf 2)		START OF THE SCRIPT		$(tput sgr 0)"


# github script start here
if git add . ; then
    echo "$(tput setaf 2)1. git add succesfull$(tput sgr 0)"
else
    echo "$(tput setaf 1)EXPCEPTION AT GIT ADD$(tput sgr 0)"
    exit $0
fi

# unset all email and name
if git config --global --unset user.name; then
    echo "$(tput setaf 2)4. git unset user.name succesfull$(tput sgr 0)"
    if git config --global --unset user.email; then
        echo "$(tput setaf 2)5. git unset user.email succesfull$(tput sgr 0)"
    else
        echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
    fi
else
    echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
fi

if git config --unset user.name; then
    echo "$(tput setaf 2)4. git unset user.name succesfull$(tput sgr 0)"
    if git config --unset user.email; then
        echo "$(tput setaf 2)5. git unset user.email succesfull$(tput sgr 0)"
    else
        echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
    fi
else
    echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
fi  

# github commit with messages
if git commit -am "$1" ; then
    echo "$(tput setaf 2)2. git commit succesfull$(tput sgr 0)"
else
    if git config user.email "krisnaputra31@mhs.mdp.ac.id"; then
        echo "$(tput setaf 1)EXPCEPTION HAPPEN TRY TO ADD USER NAME$(tput sgr 0)"
        echo "$(tput setaf 2)adding user.name succesfull$(tput sgr 0)"
        if git config user.name "krisna31"; then
            echo "$(tput setaf 1)EXPCEPTION HAPPEN TRY TO ADD USER EMAIL$(tput sgr 0)"
            echo "$(tput setaf 2)adding user.email succesfull$(tput sgr 0)"
        else
            echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
            exit $0 
        fi
    else
        echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
        exit $0 
    fi   
    if git commit -am "$1" ; then
        echo "$(tput setaf 2)2. git commit succesfull$(tput sgr 0)"
    else
        echo "$(tput setaf 1)EXPCEPTION AT GIT COMMIT$(tput sgr 0)"
        exit $0
    fi
fi

# github push
if git push ; then
    echo "$(tput setaf 2)3. git push succesfull$(tput sgr 0)"
else
    echo "$(tput setaf 1)EXPCEPTION AT GIT PUSH$(tput sgr 0)"
    exit $0
fi

if git config --global --unset user.name; then
    echo "$(tput setaf 2)4. git unset user.name succesfull$(tput sgr 0)"
    if git config --global --unset user.email; then
        echo "$(tput setaf 2)5. git unset user.email succesfull$(tput sgr 0)"
    else
        echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
    fi
else
    echo "$(tput setaf 1)EXPCEPTION AT GIT UNSET$(tput sgr 0)"
fi    

# delete history
if history -c ; then
    echo "$(tput setaf 2)6. history deleted$(tput sgr 0)"
else
    echo "$(tput setaf 1)EXPCEPTION AT HISTORY DELETED$(tput sgr 0)"
    exit $0
fi

echo "$(tput setaf 2)		COMPLETE WITHOUT ERROR		$(tput sgr 0)"