**# README #**

### What is this repository for? ###

* This repository is for testing purposes of the Budgeta website.

### How do I get set up? ###

* You need to import the project as a Maven Project from SCM (if you don't have the relevant SCM connector then download the maven-egit one).
* You need to have TestNG, Maven and egit plugins configured on your eclipse.
* The only dependency in this project is the customized reporting jar found in the libs folder (reportng-galilcustomized.jar) - please add it as a jar dependency in your project.
* All other dependencies come from maven's pom.xml.
* Please set your Java compiler to 1.6.
* Run tests using xml runners (i.e: testsRunner.xml)
* add a customized properties file concatenated with your computer's hostname. (i.e: TestProps_YourHostname.properties ,TestProps_Naels-PC.properties ).

### Contribution guidelines ###

*  Every single POM extends the class AbstractPOM.
*  Every POM needs to implement the method: isDisplayed();
*  Every method which writes in an input field starts with set…. (i.e: setUsername() , setPassword() … ) 
*  Every method which clicks on a method starts with click… (I.e: clickSearch() … ).
*  Please follow Object Oriented Design strategies.
*  Please follow the Single Responsibility Principle (SRP) meaning that a certain class needs to handle a single functionality per method on the low level and per class as a high level.
*  Prefer Composition over inheritance if possible.
*  Please follow Liskov's Substitution Principle (LSP) - meaning that if class B inherits from class A. class B cannot override class A's behavior.
*  Whenever you click something which redirects you to another page, set the return value to that POM. If that POM is not implemented yet, return the following:

// TODO: incomplete method - return value hasn't been implemented yet.

public Class<? extends AbstractPOM> methodName(){

//method scope

return null;

}

### Who do I talk to? ###


