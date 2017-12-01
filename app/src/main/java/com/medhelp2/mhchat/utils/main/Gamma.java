//package com.medhelp2.mhchat;
//
//
//import java.util.Random;
//
//
//class Gamma
//{
//    private String gammaOpen, gammaKey, gammaCypher, gammaDeCypher;
//
//    public String getGammaOpen()
//    {
//        return gammaOpen;
//    }
//
//    public void setGammaOpen(String gammaOpen)
//    {
//        this.gammaOpen = gammaOpen;
//    }
//
//    public String getGammaKey()
//    {
//        return gammaKey;
//    }
//
//    public void setGammaKey(String gammaKey)
//    {
//        this.gammaKey = gammaKey;
//    }
//
//    public String getGammaCypher()
//    {
//        return gammaCypher;
//    }
//
//    public void setGammaCypher(String gammaCypher)
//    {
//        this.gammaCypher = gammaCypher;
//    }
//
//    public String getGammaDeCypher()
//    {
//        return gammaDeCypher;
//    }
//
//    public void setGammaDeCypher(String gammaDeCypher)
//    {
//        this.gammaDeCypher = gammaDeCypher;
//    }
//
//    public String generateKey() throws Exception
//    {
//        gammaKey = "";
//
//        Random random = new Random();
//
//        if (gammaOpen == null || gammaOpen.length() == 0)
//            throw new Exception("Please input the text!");
//
//        char[] mass = gammaOpen.toCharArray();
//        char[] arr = new char[gammaOpen.length()];
//
//        for (int i = 0; i < gammaOpen.length(); i++)
//        {
//            if (mass[i] > 255)
//                arr[i] = (char) random.nextInt(4096);
//            else
//                arr[i] = (char) random.nextInt(255);
//        }
//        return String.valueOf(arr);
//    }
//
//    public void Encode()
//    {
//        try
//        {
//            gammaCypher = "";
//
//            //Checking null key
//            if (gammaKey == null || gammaKey.length() == 0)
//                throw new Exception("Key not generated!");
//
//            //Checking null open text
//            if (gammaOpen == null || gammaOpen.length() == 0)
//                throw new Exception("Please input the text!");
//
//            //Checking if key length is equal to open text
//            if (gammaOpen.length() != gammaKey.length())
//                throw new Exception("Text and key length does not match!");
//
//            //Applying xor operation symbol-by-symbol
//            for (int i = 0; i < gammaOpen.length(); i++)
//            {
//                gammaCypher += (char) (gammaOpen[i] ^ gammaKey[i]);
//            }
//                /*if (gammaCypher.Length != gammaKey.Length)
//                {
//                    throw new Exception("Косяк");
//                }*/
//        } catch (Exception e)
//        {
//        }
//    }
//
//    public void Decode()
//    {
//        try
//        {
//            gammaDeCypher = "";
//
//            //Checking null key
//            if (gammaKey == null || gammaKey.length() == 0)
//                throw new Exception("Key not generated!");
//
//            //Checking null cypher text
//            if (gammaCypher == null || gammaCypher.length() == 0)
//                throw new Exception("Please input the text!");
//
//            //Checking if key length is equal to decypher text
//            if (gammaCypher.length() != gammaKey.length())
//                throw new Exception("Text and key length does not match!");
//
//            //Applying xor operation symbol-by-symbol
//            for (int i = 0; i < gammaCypher.length(); i++)
//                gammaDeCypher += (char) (gammaCypher[i] ^ gammaKey[i]);
//        } catch (Exception e)
//        {
//        }
//    }
//
//
//}
//
