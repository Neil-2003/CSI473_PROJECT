# Verifies the dependency rule on Windows: ports and domain import only java.* and ub.accommodation.{domain,ports}
Set-Location (Join-Path $PSScriptRoot "..")
$bad = Get-ChildItem -Recurse -Filter *.java ub | Select-String -Pattern '^import ' |
  Where-Object { $_.Line -notmatch '^import (static )?(java\.|ub\.accommodation\.(domain|ports)\.)' }
if ($bad) { Write-Host "FAIL: forbidden imports:"; $bad | ForEach-Object { "$($_.Path):$($_.LineNumber): $($_.Line)" }; exit 1 }
Write-Host "OK: ports and domain depend only on java.* and ub.accommodation.{domain,ports}"
