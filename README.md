# Pocket Mode Android App

Pocket mode is an android app that detects when the phone is in your pocket and disable the fingerprint when active, it helps save the battery and avoid accident unlock while on pocket. 

**NOTE** : This is a privilged app, it needs be signed by the platform certificate and installed in `system/priv-app`


## Features:
- Detect when the phone screen is off.
- Check if the device is in Pocket.
- Disable fingerprint when device is in pocket.
- Check if the device is stationary (implemented but not used).


## Setup
Clone this repository and include in your AOSP device folder:
```bash
git clone https://github.com/islem19/Pocket-Mode-App.git
```

Include the package name on the main makefile `device_common.mk`: 
```bash
#Pocket mode App
PRODUCT_PACKAGES += AospPocketMode
```

Build Your device again with: 
```bash
#make the whole AOSP
cd $(ROOT_AOSP)
make
```
or
```bash
#make the module
cd $(POCKED_APP_FOLDER)
mm
```


## How to enable it? 
- because the app request Device Admin privileges and permissions, it won't be able to disable/enable the fingerprint if the user doesn't explicitly enable it and give it the permission. 

 **Pull request alert**: if you can bypass this permission or you have a better way to do it, feel free to contribute and send a pull request :) 


## Permissions
The App requires the following permissions:
- Request Device Administrator (To enable the app to enable/disable fingerprint).
- Bind quick settings tile (create a quick tile for the app in Quick Settings)


## Libraries and Dependencies
- Android Support Library.


## Maintainers
This project is mantained by:
* [Abdelkader Sellami](https://github.com/islem19)


## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
4. Push your branch (git push origin my-new-feature)
5. Create a new Pull Request


## License
This application is released under GNU GPLv3 (see [LICENSE](https://github.com/islem19/Pocket-Mode-App/blob/develop/LICENSE)). Some of the used libraries are released under different licenses.
