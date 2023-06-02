Section
  ; Define variables
  !define APPNAME "FoodManagement"
  !define VERSION "1.0"
  !define PUBLISHER "FenixCorp"
  !define URL "http://www.example.com"
  !define ICON "icons/download.ico"

  ; Set installer properties
  Name "${APPNAME} ${VERSION}"
  OutFile "FoodManagementSetup.exe"
  InstallDir "$PROGRAMFILES\${APPNAME}"
  InstallDirRegKey HKLM "Software\${PUBLISHER}\${APPNAME}" "Install_Dir"
  ShowInstDetails show
  ShowUnInstDetails show

  ; Set uninstaller properties
  UninstallDisplayName "${APPNAME} ${VERSION}"
  UninstallDisplayIcon "${ICON}"
  UninstallRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APPNAME}"

  ; Add files
  Section "MainSection" SEC01
  SetOutPath "$INSTDIR"
  File "myproject.jar"
  File "icons/download.ico"
  SectionEnd

  ; Add shortcuts
  Section "Shortcuts" SEC02
  CreateDirectory "$SMPROGRAMS\${APPNAME}"
  CreateShortCut "$SMPROGRAMS\${APPNAME}\${APPNAME}.lnk" "$INSTDIR\myproject.jar"
  SectionEnd
Section
