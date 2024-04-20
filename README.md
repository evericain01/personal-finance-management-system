# PFMS (Personal Financial Management System) - Java Spring Boot Application

This project is a web-based system aimed at aiding users in personal finance management. Its primary objectives are budget management, spending tracking, and financial goal planning. Additionally, the system will incorporate expense tracking and offer visual analytics to enhance users' ability to make informed financial decisions.

## Setup Instructions

1. **Open the Project in IntelliJ IDEA:**
    - After cloning the project, open it in IntelliJ IDEA to start working on it.
    - Launch IntelliJ IDEA.
    - Select "Open" or "Import" on the welcome screen.
    - Navigate to the directory where you cloned the PFMS project.
    - Select the project folder and click "Open."

   IntelliJ IDEA will import the project and set it up based on the configuration files found in the project directory.

2. **Install Dependencies with Maven:**
    - The PFMS project uses Maven for dependency management. Once you've opened the project in IntelliJ IDEA, the IDE should automatically recognize the `pom.xml` file and start downloading the necessary dependencies.

   **NOTE:** If IntelliJ IDEA doesn't automatically start importing Maven dependencies, you can trigger the process manually:
    - Open the "Maven" tool window (usually found on the right side of the IDE).
    - Click on the "Reload" button (represented by a refresh icon) to synchronize the project with the Maven dependencies.

3. **Run the Application:**
    - In IntelliJ IDEA, locate the main application class in the project explorer (named `Application.java`).
    - Right-click on the file and select "Run" from the context menu.
    - IntelliJ IDEA will compile the project and start the Spring Boot application.

4. **Verify the Application:**
    - After the application starts, verify that it's running correctly.
    - Open a web browser.
    - Navigate to [http://localhost:8080](http://localhost:8080)
    - You should see the PFMS's login screen, indicating that the application is running successfully.

## Website Demo
![](https://github.com/evericain01/personal-finance-management-system/blob/main/gify.gif)



## Building and Running the Docker Image

The project already has a Dockerfile, you can use it to build and run a Docker image for your PFMS application.

### Build the Docker Image

1. **Open a Terminal or Command Prompt:**
    - Open a terminal or command prompt on your computer.

2. **Navigate to the Project Directory:**
    - Use the `cd` command to navigate to the directory containing your Dockerfile. This is typically the root directory of your project.

3. **Build the Docker Image:**
    - Run the following command to build the Docker image (replace `my-pfms-image` with your desired image name):

   ```bash
   docker build -t my-pfms-image .


## Docker.run.aws File

The `Docker.run.aws` file was used for the hosting setup of AWS Elastic Beanstalk and AWS RDS (for the database). Originally, the project was deployed by creating and deploying a Docker image onto Elastic Beanstalk, and the database was hosted on AWS RDS. However, this hosting configuration had to be removed due to AWS starting to charge for the services since the subscription moved beyond the free tier option. So the website is not hosted.

For further details on the initial hosting setup and configuration, here is the documentation provided in the [PDF file](https://github.com/evericain01/personal-finance-management-system/files/15046574/AWS.Hosting.Documentation.pdf) (AWS Hosting Documentation.pdf) included with this repository.