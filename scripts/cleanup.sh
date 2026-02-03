#!/bin/bash

# Configuration
DRY_RUN=false
TARGET_PACKAGE="com/komodo" # Path format for directories

# Check for dry-run flag
if [[ "$1" == "--dry-run" ]]; then
    DRY_RUN=true
    echo "--- DRY RUN MODE: No files will be deleted ---"
fi

# 1. Handle "com.komodo.*" empty packages specifically
# We search for directories matching the package path
find . -type d -path "*/$TARGET_PACKAGE/*" | sort -r | while read -r dir; do
    if [ -d "$dir" ]; then
        # Check if directory is empty (ignores hidden files like .DS_Store)
        if [ "$(ls -A "$dir" | wc -l)" -eq 0 ]; then
            if [ "$DRY_RUN" = true ]; then
                echo "[Dry-Run] Would remove empty package: $dir"
            else
                echo "Removing empty package: $dir"
                rmdir "$dir"
            fi
        else
            echo "Keeping (not empty): $dir"
        fi
    fi
done

# 2. Handle all other empty source directories (src/**)
# This looks for any directory under a 'src' folder
find . -name "src" -type d | while read -r src_root; do
    find "$src_root" -type d -empty | sort -r | while read -r empty_dir; do
        if [ "$DRY_RUN" = true ]; then
            echo "[Dry-Run] Would remove empty source dir: $empty_dir"
        else
            echo "Removing empty source dir: $empty_dir"
            rmdir "$empty_dir" 2>/dev/null
        fi
    done
done