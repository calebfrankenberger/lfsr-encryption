# lfsr-image-encryption
Welcome to my LFSR Image Encryption program. This application takes in an image and encrypts it using a linear feedback shift register algorithm. You can customize the encryption process by messing around with different seeds and tap points. Once you encrypt the image the way you want it, make sure you save your image along with the seed and tap point, because you can run it back through to decrypt it back to the original.

## Instructions
To use the application, start by selecting an image you want to encrypt. Next, you'll need a seed. Valid seeds include either a string of binary (e.g. 01101010) or an alphanumeric string (e.g. HelloImASeed7). After you've picked your seed, decide on your tap location. The tap location is the position where the XOR will be performed against the first bit of the binary string. 


**You can download the program here:**
https://www.dropbox.com/scl/fi/gvo1hb38tffls1om7lbb1/lfsr-image-encryption.zip?rlkey=ro1344ubpbv8lm139vi3npm4t&dl=0

## Examples 
<details>
  <summary>Application home screen</summary>
  <p align=center>
    <img src="https://github.com/calebfrankenberger/lfsr-encryption-3/assets/69817026/bb5dd403-67f3-488e-8f27-0268aa8f512c">
  </p>
</details>
<details>
  <summary>Before vs after LFSR encryption</summary>
 <p align=center>
     <img src="https://github.com/calebfrankenberger/lfsr-encryption-3/assets/69817026/7a963763-f41d-412c-8aec-85b04ee03cb0">
 </p>
</details>

## How it works
A linear feedback shift register (LFSR) starts with an initial state, called the seed. To perform a step of the LFSR operation, first all of the bits are shifted over by one. To calculate the bit to fill in the now-empty spot, an XOR operation is done. The XOR happens with either the leftmost or rightmost bit of the seed, and the "tap" bit. Sometimes multiple tap bits (and therefore multiple XORs) can be used. 
<p align=center>
  <img src="https://github.com/calebfrankenberger/lfsr-encryption-3/assets/69817026/779539ca-d97d-442d-b24f-40c46122946d">
</p>
The diagram above shows one step of an LFSR. Usually several more steps are performed. The result of the XOR is output by each step. Multiple steps together can be used to generate psuedo-random numbers. In my program, the random numbers are used to calculate a new ARGB value for each pixel in an image. 
<br><br>
Because of the linear nature of the algorithm, running the output image through the program with the exact same seed and tap point will reverse the encryption. This happens because the mathmatical operations are the same when the same seed and tap points are used, thus creating the same values. 

## Pros and Cons of Linear Feedback Shift Registers
The LFSR demonstrated in this application is a demonstration and should not be used for encrypting any sensitive images or data. This is because the LFSR is very easy to crack. If an attacker is able to discover the seed and the tap point, they will be able to decrypt the the data. Let's go over some pros and cons of LFSR encryption: 
### Pros:
-  An LFSR is quick to run, simple to make, and compact. It can generate quite long pseudo-random phrases or values with low resource consumption.
-  To the naked eye, an image run through the LFSR (with a sufficiently long seed) is completely scrambled. Even if an LFSR is easy to crack, an encrypted image from this program definitely adds a layer of security from the human eye.
### Cons:
- The sequences generate by the LFSR are periodic and bound to repeat themselves eventually. The same seed and tap will always produce the same output, which makes the numbers generated pseudo-random rather than truly random.
- This pseduo-randomness also means if a potential attacker is able to notice a pattern in the algorithm and determine the seed and tap points, they would be able to decipher the entire image. 

There are several methods to strengthen LFSR encryption. One of the easiest ways is selecting a longer seed. Additionally, multiple tap points may be used to add more variability to the program. LFSR's are often used as one part of an encryption algorithm, rather than on their own. To demonstrate the predictablility of LFSRs, try sending an image through with a very short seed (such as 1001). 

## Potential Improvements
There are a couple changes and optimizations that would make this program even better. I'd like to implement the following:
- Option to chose multiple tap points. In order to achieve maximum period length, I would need to add the ability to tap more than one bit. This would reduce the predictibility of the algorithm because it would allow for more randomization before the register starts repeating itself.
- Automatically select the best tap point(s) based on size of seed. Building off the tap improvement from the first point, I think an option to auto choose the best taps for the length of seed would be a more user friendly experience. 
  
