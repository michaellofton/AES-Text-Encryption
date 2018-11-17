## AES-Text-Encryption
A desktop java application that allows users to encrypt or decrypt their messages using the AES algorithm.

## Features:
**DISCLAIMER:** This hasn't been cryptographically and strenuously tested. This project was primarily for research and programming practice purposes. I am not an expert with cryptography and it's very possible that the way I utilized the library and created the flow of data is insecure. The majority of the code was created by a senior in high school with (at the time) 1 year programming experience in Java. **USE THIS FREEWARE PRODUCT AT YOUR OWN RISK. DO NOT USE FOR SERIOUS AND LIFE-THREATENING APPLICATIONS.**

- Portable (works on desktop and laptop machines that have Java (AKA the Java Runtime Environment (JRE)) installed).
  - Download Java here: https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
- AES 128-bit encryption for ASCII textual data (does not include the extended ASCII table). 
- Automatically generate symmetric secret keys and encrypted messages to send to a party you wish to communicate with privately. 
  Note: Make sure the key is sent through a secure and private channel. The encrypted message can be sent through insecure or
  secure channels, but if the key is sent through an insecure channel (for example, a publically accessible online area like an online forum), then the message could be compromised. 
- Graphical user interface for improved and simplified usability. 
- Amount of time taken to encrypt and decrypt messages displayed.

## Uses:      
Possible applications and usage for this program include:
- Privatizing and protecting information, such as urls, contents of documents, and anything else that's in an ASCII textual format.
- Sending secret messages to friends, family, and colleagues
- Statistically measuring and analyzing the speed of encryption (the encryption/decryption speed is visible for this purpose).

## Design Features
The majority of the functionality of the program was written in Java while parts of the graphical user interface and event handling were made using JavaFX. 

The program was created using the Model-view-controller design pattern, which creates a communication of data from the model (the part that performs encryption on text), the view (what the user sees) and the controller (the part that updates the view using information from the model). 

