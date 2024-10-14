#!/usr/bin/env zsh
set -euo pipefail

list_limit=500
cutoff_date=$(date -v -2d +"%Y-%m-%dT%H:%M:%SZ")

cat <<EOF

GitHub Actions execution runs management for Welcome Clerk:

>>> <https://github.com/Mimis-Latlaeg-Hattalag/welcome-clerk> |||

Action Runs:

EOF

# Extract specific fields with jq, compare the updatedAt field to the cutoff date, and add index
index=0
gh run list --all --limit $list_limit --json 'event,attempt,conclusion,startedAt,updatedAt,workflowName,workflowDatabaseId,databaseId,status,displayTitle' | \
  jq -r '.[] | "[\(.event):\(.attempt)-\(.conclusion)-[\(.startedAt) - \(.updatedAt)]-\(.databaseId)] -- \t\(.status): \(.displayTitle) - \(.workflowName) - \(.workflowDatabaseId)"' | \
    while read -r row; do
      index=$((index + 1))
      echo -e "${index} => \t$row"
    done

# Deleting workflow runs older than 2 days
del_index=0
gh run list --all --limit $list_limit --json 'databaseId,updatedAt' | \
  jq --arg cutoff "$cutoff_date" -r '.[] | select(.updatedAt < $cutoff) | .databaseId' | \
  while read -r databaseId; do
    index=$((del_index + 1))
    echo -e "${del_index} => \t Deleting run: $databaseId (older than 2 days)"
    gh run delete "$databaseId"
done

echo -e "\nListed: $index, Deleted: $del_index.\n"

# Fields of interest:
#
# attempt
# conclusion
# createdAt
# databaseId
# displayTitle
# event
# headBranch
# headSha
# name
# number
# startedAt
# status
# updatedAt
# url
# workflowDatabaseId
# workflowName
