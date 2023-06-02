[Setup]
AppName=Food Management
AppVersion=1.0
DefaultDirName={pf}\FoodManagement
OutputDir=.
OutputBaseFilename=FoodManagementSetup
Compression=lzma2
SolidCompression=yes
DisableDirPage=yes
DisableProgramGroupPage=yes
UninstallDisplayIcon={app}\download.ico
UninstallDisplayName=Food Management

[Files]
Source: "C:\Users\Asus\Desktop\2ème\projet java s2\CRUD APP\java_bd\FoodManagement.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Asus\Desktop\2ème\projet java s2\CRUD APP\java_bd\icons\download.ico"; DestDir: "{app}"; Flags: ignoreversion
Source: "C:\Users\Asus\Desktop\2ème\projet java s2\CRUD APP\java_bd\lib\ojdbc11_g.jar"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
Name: "{commonprograms}\Food Management"; Filename: "{app}\FoodManagement.jar"; WorkingDir: "{app}"; IconFilename: "{app}\download.ico"

[Run]
Filename: "{app}\FoodManagement.jar"; Description: "Launch Food Management"; Flags: nowait postinstall skipifsilent
