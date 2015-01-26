# Percolate_Mann

## Requirements Met
The following requirements were met in developing the Coffee Application:
* built using Android Studio
* targets API 14 - 21
* Network calls made with Volley along with jackson for network parsing
* Sent api_key as Authorization HTTP header

## Other Highlights
* the app uses a RecyclerView for the list, ItemDecorator for dividers and Custom Click Listener
* The app’s theme is “Theme.AppCompat.Light.DarkActionBar” to allow use of Material design features.
* A fading NetworkImageView is used to fade in images loaded from the network.
* Styles are used for each of the basic font types
* A fade in transition is used when exiting the Splash Screen.  Slide transitions are used for other screens.

## Basic Flow
The SplashActivity displays an initial screen and is used to upload the initial JSON Array.  It sets up the the RequestQueue and LRU Image Cache in Volley.  The cache size is set to 20 images.

A list is displayed after the first JSON file is loaded and a minimum of 3 seconds have passed.  The list uses Android’s RecyclerView along with several custom components for dividers and a row touch listener.  Volley’s NetworkImageView is used to upload images given a url.

A detail view is shown after the user clicks on the list.  Sharing is achieved through Android’s ACTION_SEND Intent and a Chooser which show available applications for sharing.


## Would have liked to have added
I started adding code to scale images in a background thread, but ran out of time.  Many additional sharing features could have been added for integration with Facebook, Twitter and other social media.
