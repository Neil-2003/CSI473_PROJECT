#!/usr/bin/env bash
# Verifies the dependency rule: ports and domain import only java.* and ub.accommodation.domain / ports.
# Run from src/interfaces-or-ports:  bash tools/check-ports.sh
cd "$(dirname "$0")/.." || exit 2
bad=$(grep -rnE '^import ' ub | grep -vE 'import (static )?(java\.|ub\.accommodation\.(domain|ports)\.)')
if [ -n "$bad" ]; then echo "FAIL: forbidden imports in ports/domain:"; echo "$bad"; exit 1; fi
echo "OK: ports and domain depend only on java.* and ub.accommodation.{domain,ports}"
