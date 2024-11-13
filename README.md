## Setup instruction

#### 1. Install Android Studio & JDK 

- Download the latest version of JDK: 
  https://www.oracle.com/java/technologies/downloads/
- Download the latest version of Android Studio: [https://developer.android.com/studio](https://developer.android.com/studio).
- Run the installer and follow the setup instructions.
- After installation, open Android Studio and complete the initial setup.

#### 2. Clone the Project from GitHub

- Copy the repository link from GitHub.
- In Android Studio, select **File** > **New** > **Project from Version Control...**.
- In the **URL** field, paste the repository link, choose a folder on your local machine to store the project, and click **Clone**.
- Android Studio will download the project files and open it.

#### 3. Wait for Dependencies to Load

- Android Studio will automatically begin downloading all project dependencies via Gradle (this may take some time).
- If any errors occur during synchronization, follow Android Studio’s prompts to install missing packages or adjust Gradle settings.

#### 4. Set Up a Configuration to Run the App

- Go to **Run** > **Edit Configurations...**.
- Ensure that a configuration to run the app exists. If it’s missing, create a new one:
 - Click the **"+"** in the upper left corner and select **Android App**.
 - In the **Module** field, select the app module (usually `app`).


 - Click **Apply** and  **OK** to save.

#### 5. Start the Emulator

- In Android Studio, open **Device Manager** .
- Click **Create Device** and select a suitable device.
- Follow the instructions to set up the emulator and launch it.

#### 6. Run the App

- Ensure the correct run configuration (e.g., `app`) is selected in the configuration dropdown menu.
- Click the green **Run** button (or press **Shift + F10**) to start the app.
- The app will launch on the connected device or emulator.

## Environment & Tools

### Android version 

- target - API 34
- minimum - API 24
- compile - API 35

### Third-party libraries & frameworks

- firebase-bom:33.5.1
- firebase-analytics
- firebase-messaging:23.1.0
- material3:1.0.1

### Build and testing tools

- Android Studio Ladybug | 2024.2.1 Patch 2

## Debug steps

### 1. Web View Implementation Issues

   - **Issue:** Web view fails to load the specified URL.

     - **Solution:** Ensure `INTERNET` permission is enabled in the `AndroidManifest.xml`.
     - **Solution:** Check the URL syntax. Use the `https://` or `http://` prefix for all URLs.
     - **Solution:** Verify network connectivity on the device or emulator.



   - **Issue:** Navigation controls (back, forward, refresh) not functioning.

     - **Solution:** Check the WebView’s navigation methods. Ensure you are using `goBack()`, `goForward()`, and `reload()` for these controls.
     - **Solution:** Verify if the WebView history is available for `goBack()` and `goForward()`.

   - **Issue:** External links open outside the app.

     - **Solution:** Set a WebViewClient in the WebView to handle URLs within the app.

### 2. Native Login Screen Implementation Issues (Option A)

   - **Issue:** Login button doesn’t respond or app crashes on login.

     - **Solution:** Confirm that the `onClick` event for the button is correctly defined.

   - **Issue:** Validation doesn’t work correctly (e.g., username or password fields allow invalid input).

     - **Solution:** Check the validation logic, ensuring minimum character length requirements are properly implemented. 
     - **Solution:** Ensure validation feedback is visible to the user, and confirm it’s in the correct UI thread.

   - **Issue:** Login leads to the WebView without validation or validation error messages.

     - **Solution:** Ensure validation is checked before proceeding to the WebView activity.

### 3. Push Notifications Implementation Issues (Option B)

   - **Issue:** Notifications do not appear.

     - **Solution:** Verify notification permissions are granted in `AndroidManifest.xml`.
     - **Solution:** Confirm that Firebase (or the notification service) is set up correctly if you’re using it.
  	 - **Solution:** Check the notification settings for the device or emulator to ensure notifications are not disabled for the app.

   - **Issue:** Notification enable/disable settings do not work.

     - **Solution:** Check that the toggle UI correctly calls the notification enable/disable functions in the app.
     - **Solution:** If using SharedPreferences, confirm that notification settings are being saved and retrieved correctly.

## QA 

### 1. Login Functionality (if implemented)

   - **Input Validation**

     - Verify that the username and password fields enforce minimum character requirements.
     - Check that validation error messages display for inputs that don’t meet criteria.
   - **Login Flow**

     - Confirm that successful login transitions to the WebView screen.
     - Ensure failed login attempts prompt the user with an appropriate error message without proceeding.
   - **UI and UX**

     - Verify that the login button responds to taps/clicks.
     - Check for a consistent and intuitive layout across different screen sizes and orientations.

### 2. WebView Functionality

   - **URL Loading**

     - Confirm that the WebView loads the specified URL as expected.
     - Ensure the WebView displays content within the app without redirecting to an external browser.

   - **Security**

     - Check that only secure (HTTPS) URLs are permitted to load, if applicable.

   - **Error Handling**

     - Verify that an error message or custom error page displays if the WebView fails to load the URL.
     - Check that error messages are clear and provide guidance to users.

### 3. Push Notification Functionality 

   - **Notification Display**

     - Confirm that notifications appear as expected when enabled.
     - Verify that notifications include the correct title, message, and icon.

   - **Enable/Disable Settings**

     - Check that the user can enable and disable notifications within the app.
     - Verify that notifications are suppressed when the setting is disabled.

   - **Permission Handling**

     - Ensure the app correctly handles notification permissions, prompting the user if needed.
     - Check that the app gracefully handles cases where notification permissions are denied.

### 4. Basic Navigation Controls

   - **Back Navigation**

     - Verify that the back button allows navigation to the previous page in the WebView.
     - Ensure that pressing the back button on the first page closes the WebView.

   - **Forward Navigation**

     - Check that the forward button correctly navigates to the next page, if available.

   - **Refresh Functionality**

     - Confirm that the refresh button reloads the current page in the WebView.
     - Ensure that page refresh behavior is consistent and responsive.

### 5. Error Handling

   - **Network Errors**
     - Ensure the app detects network connectivity issues and notifies the user.
     - Check that the app automatically attempts to reconnect or prompts the user to refresh when connectivity is restored.
