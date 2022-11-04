package com.main;
import tag.Tag;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String;

public class Main {

    static int isFound(String txt, String target, int pointer) {
        int found = -1;
        for (int i = 0; i <= pointer -  target.length(); ++i) {
            if (target.length() > 0 && target.charAt(0) == txt.charAt(i)) {
                int f = 0;
                for (int j = i, k = 0; k < target.length(); ++k, ++j) {
                    if (target.charAt(k) == txt.charAt(j))
                        f++;
                    else
                        break;
                }
                if (f == target.length())
                    found = pointer - i;
            }
        }
        return found;

    }

    static int NumOfBits(int x){
        if(x==0)
            return 1;
        int ans=0;
        while (x>0){
            x/=2;
            ans++;
        }
        return ans;
    }


    static void Compression(String txt){
        System.out.println("-------------------------------------");
        ArrayList<Tag>tags=new ArrayList<>();
        int txtSize=txt.length();
        String target = "";
        for (int pointer = 0; pointer < txtSize; ++pointer) {
            target += txt.charAt(pointer);
            int found = isFound(txt, target, pointer + 1 - target.length());
            if (found == -1) {
                String lastTarget = target.substring(0,target.length()-1);
                int lastFound = isFound(txt, lastTarget, pointer - lastTarget.length());
                Tag tag =new Tag(((lastFound == -1) ? 0 : lastFound),target.length() - 1,target.charAt((target.length()-1)));
                tags.add(tag);
                target = "";
            }
        }
        if (target.length() != 0) {

            String lastTarget = target.substring(0,target.length()-1);
            int lastFound = isFound(txt, lastTarget, txtSize - lastTarget.length());
            Tag tag =new Tag(((lastFound == -1) ? 0 : lastFound),target.length() - 1,target.charAt((target.length()-1)));
            tags.add(tag);
        }
        int mxBacking = -1000000000, mxLen = -1000000000, mxChar = -1000000000;
        for (Tag  tag: tags) {

            mxBacking= Math.max(mxBacking,tag.backing);
            mxLen= Math.max(mxLen,tag.len);
            mxChar= Math.max(mxChar,tag.nextChar);
            System.out.println("<"+tag.backing+", "+tag.len+", "+tag.nextChar+">");
        }
        System.out.println("-------------------------------------");
        mxBacking=NumOfBits(mxBacking);
        mxLen=NumOfBits(mxLen);
        mxChar=NumOfBits(mxChar);
        int TagSize=mxBacking+mxLen+8;
        // mxChar can be 8 byte to generalize
        System.out.println("The Size of the Tag = "+mxBacking+" + "+mxLen+" + 8 = "+TagSize+" Bits");
        System.out.println("THe Size After Compression = "+tags.size()*TagSize + " Bits");
        System.out.println("THe Size Before Compression = "+txtSize*8 +" Bits");
    }

    static void Decompression(ArrayList<Tag>tags){
        String decoding="";
        int n=tags.size();
        for(int i=0;i<n;i++)
        {
            int x=tags.get(i).backing;
            int y=tags.get(i).len;
            char z=tags.get(i).nextChar;
            int curr=decoding.length();
            for (int j = curr-x; j <curr-x+y ; ++j) {
                decoding+=decoding.charAt(j);
            }
            decoding+=z;
        }
        System.out.println("Decompression: "+decoding);


    }




    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("----------------------------");
        System.out.println("--- Welcome To LZ_77 App ---");
        System.out.println("----------------------------");

        while (true){
            System.out.println("Choose Number From Our List To Start: ");
            System.out.println("-------------------------------------");
            System.out.println("1- Compression");
            System.out.println("2- Decompression");
            System.out.println("3- Exit");
            System.out.println("-------------------------------------");
            System.out.print(">> ");
            int choice=input.nextInt();
            System.out.println("-------------------------------------");

            if(choice==1)
            {
                System.out.println("Enter Text To Compression: ");
                String txt =input.next();
                Compression(txt);
                System.out.println("-------------------------------------");
                System.out.println("-------------------------------------\n");


            }else if(choice==2){
                ArrayList<Tag>tags=new ArrayList<>();
                System.out.println("Enter Tags' Size: ");
                int numOfTags=input.nextInt();
                for (int i=0;i<numOfTags;i++){
                    int backing = input.nextInt();
                    int Len=input.nextInt();
                    char nextChar=input.next().charAt(0);
                    Tag tag =new Tag(backing,Len,nextChar);
                    tags.add(tag);
                }
                System.out.println("-------------------------------------");
                Decompression(tags);
                System.out.println("-------------------------------------");
                System.out.println("-------------------------------------\n");

            }else if(choice==3) {
                System.out.println("Exit..");
                System.out.println("-------------------------------------");
                System.out.println("-------------------------------------\n");
                break;
            }
        }





    }
}
/**

 8
 0 0 a
 0 0 b
 2 1 a
 3 2 b
 5 3 b
 2 2 b
 5 5 b
 1 1 a

 abaababaabbbbbbbbbbbba
 ABAABABAABBBBBBBBBBBBA

 **/
