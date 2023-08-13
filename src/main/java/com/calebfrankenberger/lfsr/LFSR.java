package com.calebfrankenberger.lfsr;

public class LFSR {
    private char[] _seed = {'0'};
    private int _tap = 0;
    public LFSR(String seed, int tap) {
        this._seed = seed.toCharArray();
        this._tap = _seed.length-tap-1;
    }

    // Simulate one step and return the feedback (resulting from the XOR of the leftmost bit and the tap bit)
    public int step() {
        int tappedBit = getBitAt(_tap);
        int next = (getBitAt(0) ^ tappedBit); // Compute the next bit by XORing the tapped bit

        for(int i = 0; i < _seed.length-1; i++) {
            _seed[i] = _seed[i+1];
        }

        // Set the first bit of the string using the bit calculated above
        _seed[_seed.length-1] = Character.forDigit(next, 10);
        return next;
    }

    // Simulate a set number of steps
    public int generate(int numOfSteps) {
        int output = 0;
        for(int i = 0; i < numOfSteps; i++)
            output = (output*2) + step();
        return output;
    }

    // Return the number of bits in the register
    public int length() {
        return _seed.length;
    }

    // Return bit as either 0 or 1
    public int getBitAt(int i) {
        char charAtI = _seed[i];
       if(charAtI == '1')
            return 1;
       return 0;
    }

    // Return a string representation of the register
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (char c : _seed)
            output.append(c);
        return output.toString();
    }

}
