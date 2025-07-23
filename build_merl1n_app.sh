#!/bin/bash
set -e

APP_NAME="Merl1n"
JAR_FILE="m1shell.jar"
ICON_FILE="icon.icns" # Pokud nemáš, zakomentuj kopírování a řádek v Info.plist

APP_DIR="${APP_NAME}.app"
CONTENTS_DIR="${APP_DIR}/Contents"
MACOS_DIR="${CONTENTS_DIR}/MacOS"
RESOURCES_DIR="${CONTENTS_DIR}/Resources"
PLIST_FILE="${CONTENTS_DIR}/Info.plist"

# Vyčisti staré buildy
rm -rf "$APP_DIR"
mkdir -p "$MACOS_DIR" "$RESOURCES_DIR"

# Vytvoř Info.plist
cat > "$PLIST_FILE" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN"
 "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>CFBundleName</key>
    <string>$APP_NAME</string>
    <key>CFBundleDisplayName</key>
    <string>$APP_NAME</string>
    <key>CFBundleIdentifier</key>
    <string>cz.ivondrak.$(echo "$APP_NAME" | tr '[:upper:]' '[:lower:]')</string>
    <key>CFBundleVersion</key>
    <string>1.0</string>
    <key>CFBundleExecutable</key>
    <string>launch.sh</string>
    <key>CFBundlePackageType</key>
    <string>APPL</string>
    <key>LSMinimumSystemVersion</key>
    <string>10.12</string>
    <key>CFBundleIconFile</key>
    <string>icon.icns</string>
</dict>
</plist>
EOF

# Vytvoř launch.sh
cat > "${MACOS_DIR}/launch.sh" <<EOF
#!/bin/bash
cd "\$(dirname "\$0")"
exec java -jar ../Resources/$JAR_FILE
EOF

chmod +x "${MACOS_DIR}/launch.sh"

# Zkopíruj .jar
cp "$JAR_FILE" "$RESOURCES_DIR/"

# Zkopíruj ikonu (volitelné)
if [ -f "$ICON_FILE" ]; then
  cp "$ICON_FILE" "$RESOURCES_DIR/"
  echo "✅ Ikona přidána: $ICON_FILE"
else
  echo "⚠️  Ikona nenalezena: $ICON_FILE (přeskočeno)"
fi

# Výsledek
echo "✅ Aplikace vytvořena: $APP_DIR"
echo "📦 Spusť ji příkazem: open \"$APP_DIR\""