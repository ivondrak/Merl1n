#!/bin/bash
set -e

# === Nastavení ===
APP_NAME="Merl1n"
JAR_FILE="m1shell.jar"
ICON_FILE="icon.icns"
DEVELOPER_ID="Developer ID Application: Ivo Vondrák (VA5H4K5VZQ)"  # ZMĚŇ
KEYCHAIN_PROFILE="ivo-profile"                                     # ZMĚŇ
APP_DIR="${APP_NAME}.app"
DMG_NAME="${APP_NAME}.dmg"

# === Vytvoření .app ===
./build_merl1n_app.sh

# === Podepsání ===
echo "✍️ Podepisuji aplikaci..."
codesign --deep --force --options runtime --verify \
  --sign "$DEVELOPER_ID" "$APP_DIR"

codesign --verify --deep --strict --verbose=2 "$APP_DIR"
echo "✅ Podepsáno"

# === Vytvoření .dmg ===
echo "💽 Vytvářím $DMG_NAME..."
rm -f "$DMG_NAME"
hdiutil create -volname "$APP_NAME" \
  -srcfolder "$APP_DIR" \
  -ov -format UDZO "$DMG_NAME"
echo "✅ Vytvořen $DMG_NAME"

# === Notarizace ===
echo "📤 Odesílám k notarizaci..."
xcrun notarytool submit "$DMG_NAME" \
  --keychain-profile "$KEYCHAIN_PROFILE" \
  --wait

# === Staple ===
echo "📎 Připojuji staple..."
xcrun stapler staple "$DMG_NAME"
xcrun stapler validate "$DMG_NAME"

echo "🎉 Hotovo! Výsledný soubor: $DMG_NAME"