#!/bin/bash

# Configuration
OLD_PKG="com.komodo"
NEW_PKG="ca.glong.komodo"
OLD_PATH="com/komodo"
NEW_PATH="ca/glong/komodo"

DRY_RUN=false
if [[ "$1" == "--dry-run" ]]; then
    DRY_RUN=true
    echo "--- DRY RUN MODE ---"
fi

# 1. Update file contents (Package declarations and imports)
echo "Updating source code references..."
# Find all .kt files and replace the package/import strings
if [ "$DRY_RUN" = false ]; then
    find . -name "*.kt" -type f -exec sed -i '' "s/$OLD_PKG/$NEW_PKG/g" {} +
else
    echo "[Dry-Run] Would update strings in .kt files: $OLD_PKG -> $NEW_PKG"
fi

# 2. Move the directory structure
# We look for the 'kotlin' root to ensure we are moving within the source sets
find . -name "kotlin" -type d | while read -r kotlin_dir; do
    SOURCE_DIR="$kotlin_dir/$OLD_PATH"

    if [ -d "$SOURCE_DIR" ]; then
        TARGET_DIR="$kotlin_dir/$NEW_PATH"

        if [ "$DRY_RUN" = true ]; then
            echo "[Dry-Run] Would move $SOURCE_DIR to $TARGET_DIR"
        else
            echo "Migrating: $SOURCE_DIR -> $TARGET_DIR"
            # Create the new directory path
            mkdir -p "$(dirname "$TARGET_DIR")"
            # Move the contents (including sub-packages like .core.domain)
            mv "$SOURCE_DIR" "$TARGET_DIR"

            # Clean up the old empty 'com' parent if it's now empty
            rmdir -p "$kotlin_dir/com" 2>/dev/null
        fi
    fi
done

# 3. Clean up empty dirs (reusing logic from previous step)
find . -type d -empty -delete 2>/dev/null

echo "Done!"