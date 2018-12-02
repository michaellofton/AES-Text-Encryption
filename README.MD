## AES-Text-Encryption
A desktop Java application that allows users to encrypt or decrypt their messages using the AES algorithm. 

<img src="/img/How To Encrypt-Decrypt.png" style="text-align:center"/>

## Features:
**DISCLAIMER:** This program hasn't been __cryptographically__ tested (cryptanalized). This project was initially created for research of cryptography and programming practice purposes. I am not an expert with cryptography and it's possible that the way I utilized java's packages and created the flow of data is insecure. The majority of the code was created by a senior in high school with (at the time) 1 year of programming experience in Java. **DO NOT USE FOR SERIOUS AND LIFE-THREATENING SITUATIONS.**

- Portable (works on desktop and laptop machines that have Java (the Java Runtime Environment (JRE)) installed).
  - Download Java here: https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
- AES 128-bit encryption for ASCII textual data (does not include the extended ASCII table). 
- Graphical user interface for improved and simplified usability. 
- Amount of time taken to encrypt and decrypt messages displayed.
- Automatically generate symmetric secret keys and encrypted messages to send to a party you wish to communicate with privately. 
  **Note:** Make sure the key is sent through a secure and private channel. The encrypted message can be sent through insecure or
  secure channels, but if the key is sent through an insecure channel (for example, a publicly accessible online area like an online forum or a YouTube video's comments section), then the message could be compromised. 


## Uses:      
Possible applications and usage for this program include:
- Privatizing and protecting information, such as urls, contents of documents, the text from emails, and anything else that's in an ASCII textual format.
- Sending secret messages to friends, family, and colleagues
- Statistically measuring and analyzing the speed of encryption (the encryption/decryption speed is visible for this purpose).

## Design Features
The majority of the functionality of the program was written in Java while parts of the graphical user interface and event handling were made using FXML and JavaFX.

The act of encryption and decryption relies on the utilization of the Java Cryptography Architecture (JAC), which offers packages that provide implementation of cryptographic and authentication algorithms. From the cryptography architecture, the model of this project primarily uses the crypto and security packages, which are used for secret key generation, IV (initialization vector) creation, and AES cipher instantiation. Both the initial bytes for the secret key and the IV are generated using the SecureRandom class (a crytographically secure pseudo random number generator (PRNG)) from Java's security package.

Error handling measures were used to prevent the program from crashing upon invalid user input. Depending on the type of error, the program uses a label widget to inform the user if any bad input was detected. Once bad input is detected, the program changes the text of the label to help the user know which type of input to provide to resolve the error.

The program was created using the Model-view-controller design pattern, which creates a communication of data from the model (the part that performs encryption on text), the view (what the user sees, found in the FXML file) and the controller (the part that updates the view using information from the model). 
