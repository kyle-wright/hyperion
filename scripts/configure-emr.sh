#! /usr/bin/env bash

# Usage: $0 envrionments e.g. $0 ORG_ENV=prod

for henv in $@; do
    echo "export $henv" >> /home/hadoop/hyperion_env.sh
done
