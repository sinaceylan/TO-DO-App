# TO-DO-App

This repository is for the TO-DO-App, a task management application. Below are the steps to set up Fastlane and Screengrab for this project.

## Fastlane Installation

### Steps:

1. **Check Ruby Version**
    ```bash
    ruby --version
    ```
    *Note: The version should be 2.5 or newer.*

2. **Check for Brew**  
    ```bash
    brew —version
    ```

3. **Install Brew** (If not installed)  
    ```bash
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
    ```

4. **Download rbenv for External Ruby Installation**  
    ```bash
    brew install rbenv
    ```

5. **Add the Following to Your Profile**  
    ```bash
    eval "$(rbenv init -)"
    ```

6. **Check rbenv Version and Optionally Choose a Ruby Version**  
    ```bash
    rbenv install -l
    rbenv install 2.7.6
    ```

7. **Set the New Ruby Version as Global**  
    ```bash
    rbenv global 2.7.6
    ```

8. **Rehash rbenv**  
    ```bash
    rbenv rehash
    ```

9. **Check Which Ruby is Being Used**  
    ```bash
    which ruby
    ```

10. **Install Bundler and Create a `./Gemfile` in the Root Directory**  
    ```bash
    gem install bundler
    ```

11. **Use This Every Time You Use Fastlane**  
    ```bash
    bundle exec fastlane
    ```

12. **Update Fastlane**  
    ```bash
    bundle update fastlane
    ```

13. **If You Need to Add Some PATHs (e.g., export PATH="$HOME/.fastlane/bin:$PATH"), Use:**  
    ```bash
    vim  ~/.zshrc
    ```

14. **After Following These Steps, Reset the Terminal or Type:**  
    ```bash
    fastlane init
    ```

## Screengrab Installation

### Steps:

1. **Install the Gem and Add Necessary Dependencies**  
    ```bash
    gem install fastlane
    ```

2. **Since We Will Use Some APKs During the Screengrab Process, Create Some Files to Access These APKs**  
    ```bash
    ./gradlew assembleDebug assembleAndroidTest
    ```

3. **If You Get a Java Error, Most Likely Java is Not Installed on Your Computer. You Can Easily Install Java 11 With:**  
    ```bash
    brew install openjdk11
    ```

4. **Then, You Can Start Taking Screenshots With:**  
    ```bash
    fastlane screengrab
    ```

---

You can simply copy and paste the above markdown content into your GitHub README.md file.
