# FTC Team Atlas #20927

<div class="row">
  <img src="https://user-images.githubusercontent.com/83014418/159108844-31421b31-b0c7-4a9b-9a34-9f3196391e0b.png" width="350">
  
  <img src="https://user-images.githubusercontent.com/83014418/159163359-e3387297-24d7-48ea-965b-618e22dbaff8.jpeg" width="350">
</div>

Autonoumous programming for FTC Team Atlas #20927, competing in the [2021-22 Challenge](https://youtu.be/I6lX12idAf8)

## Points Breakdown

- Freight at shipping hub (6)
  - If placed on correct level based on duck (10)
  - If placed on correct level based on team marker (20)
- Deliver duck (10)
- Freight at alliance storage (2)
- Parked completely  in storage unit (6)
- Parked completely in closest warehouse (10)

## Our Stratedy

- A fixed, static path can be set for the robot to deploy the freight and then park in the alliance warehouse or alliance storage unit.

<img src="https://user-images.githubusercontent.com/83014418/159151614-9f09e7b5-b1b0-480b-b4a2-d4ec89f6d909.svg" width="500">

## Use of Encoders

- We decided to use encoders to make the robot move in a specific path.
- An encoder is a sensing device that provides feedback. Encoders convert motion to an electrical signal that can be read by some type of control device in a motion control system, such as a counter or PLC. The encoder sends a feedback signal that can be used to determine position, count, speed, or direction.  
- A control device can use this information to send a command for a particular z function.
- Encoders use different types of parameters to create a signal, including: mechanical, magnetic, resistive and optical – optical being the most common. In optical sensing, the encoder provides feedback based on the interruption of light. 
- Other parameters include speed, distance, RPM, position among others. Applications that use encoders or other sensors to control specific parameters are often referred to as closed-loop feedback or closed-loop control systems.
- For complex movement like strafing and diagonal movement for our mecanum wheels we had to use trial and error and our knowledge of vectors to create code for the autonomous period. 

## Machine Learning

- In the autonomous period, there is a twenty point bonus for placing the pre-loaded freight on the right level by detecting the position of a placed shipping element. - To solve this challenge, we decided to use a machine learning model to detect the position of the shipping element and accordingly preload the freight to the right level.
- We mounted our Logitech 310 Webcam to our robot and took videos of our shipping element on a variety of different backgrounds.
- Then, we used Computer Vision to extract the images of shipping element from the background
- To train the model, we adopted a deep learning approach using the SSD Mobile Net v2 Convolutional Neural Network.
- We decided to use a 8 bit quantization to make the model lite and easy to run on our control hub. 
- Using Vuforia and Tensorflow Lite we configured our camera and model, to accurately detect the position of the shipping element and move the arm to the appropriate position to provide us with 20 bonus points.

### Precision

<img src="https://user-images.githubusercontent.com/83014418/159152223-dcd7037a-9c88-4f1a-80a1-890f7766d7b4.png" width=400>

### Loss

<img src= "https://user-images.githubusercontent.com/83014418/159152140-4df93d6b-67a1-4152-aa11-5ad857b607fe.png" width=400>

### Model in Action

<img src= "https://user-images.githubusercontent.com/83014418/159152316-b4b872a6-1d31-4415-94b4-04add62f06f9.png" width=450>
