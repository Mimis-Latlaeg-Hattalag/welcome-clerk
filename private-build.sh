#!/usr/bin/env zsh
# rdd13r 20241013

# Function to check and source user startup files
function source_startup_files {
    # Check if the shell is interactive
    if [[ $- == *i* ]]; then
        echo -e "Interactive shell detected -- nothing to change \t [OK] ...\n"
    else
        echo -e "Non-interactive shell detected -- checking and sourcing startup \t [\?\!] ..."
        if [[ -z "$SDKMAN_DIR" || ! -s "$SDKMAN_INIT" ]]; then
            echo -e "SDKMAN is not bootstrapped yet -- sourcing startups \t [__] ..."
            source "$HOME/.zprofile" 2>/dev/null
            source "$HOME/.zshenv" 2>/dev/null
            source "$HOME/.zshrc" 2>/dev/null
        else
            echo -e "SDKMAN is already bootstrapped -- nothing to do \t [OK]."
        fi
    fi
}

cat <<EOF

Welcome Clerk Local-Magic Bootstrapper <https://github.com/Mimis-Latlaeg-Hattalag/welcome-clerk> |||

   Local artifacts builder for Welcome Clerk.
   This script is meant to run on specific machines.

Building with secret sauce...

EOF

source_startup_files
gradle --console=plain clean build

echo -e "\n\n... happy clerk is slurping secret sauce.\n\nDone!\n\nBye!\n"
