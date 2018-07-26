package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuestionBank {


    public static final class Science {

        public static final String text = "science";

        public static final class Easy {

            public static final String text = "easy";

            public static final class Q1 {
                public static final String text = "How many lungs does the human body have?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("One");
                    answersText.add("Two");
                    answersText.add("Three");
                    answersText.add("Four");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the human body’s biggest organ?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Skin");
                    answersText.add("Liver");
                    answersText.add("Heart");
                    answersText.add("Brain");
                }
            }

            public static final class Q3{
                public static final String text = "Which kind of waves are used to make and receive cellphone calls?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("Radio waves");
                    answersText.add("Visible light waves");
                    answersText.add("Sound waves");
                    answersText.add("Gravity waves");
                }
            }

            public static final class Q4{
                public static final String text = "What does a light-year measure?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Brightness");
                    answersText.add("Time");
                    answersText.add("Distance");
                    answersText.add("Weight");
                }
            }

            public static final class Q5{
                public static final String text = "The loudness of a sound is determined by what property of a sound wave?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Frequency");
                    answersText.add("Wavelength");
                    answersText.add("Velocity or rate of change");
                    answersText.add("Amplitude or height");
                }
            }
        }

        public static final class Moderate {

            public static final String text = "moderate";

            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the main gas found in the air we breathe?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Hydrogen");
                    answersText.add("Oxygen");
                    answersText.add("Helium");
                    answersText.add("Nitrogen");
                }
            }

            public static final class Q3{
                public static final String text = "Which of these elements is needed to make nuclear energy and nuclear weapons?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Sodium chloride");
                    answersText.add("Uranium");
                    answersText.add("Nitrogen");
                    answersText.add("Carbon dioxide");
                }
            }

            public static final class Q4{
                public static final String text = "Which of these people developed the polio vaccine?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Marie Curie");
                    answersText.add("Isaac Newton");
                    answersText.add("Albert Einstein");
                    answersText.add("Jonas Salk");
                }
            }

            public static final class Q5{
                public static final String text = "Which of these terms is defined as the study of how the positions of stars and planets can influence human behavior?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("Astrology");
                    answersText.add("Alchemy");
                    answersText.add("Astronomy");
                    answersText.add("Meterology");
                }
            }
        }

        public static final class Tough {
            public static final String text = "tough";

            public static final class Q1 {
                public static final String text = "At room temperature, what is the only metal that is in liquid form?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("Mercury");
                    answersText.add("Gold");
                    answersText.add("Copper");
                    answersText.add("Silver");
                }
            }

            public static final class Q2{
                public static final String text = "Which is the outermost planet in the solar system?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Mercury");
                    answersText.add("Uranus");
                    answersText.add("Neptune");
                    answersText.add("Pluto");
                }
            }

            public static final class Q3{
                public static final String text = "Milk contains water";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("70%");
                    answersText.add("75%");
                    answersText.add("90%");
                    answersText.add("80%");
                }
            }

            public static final class Q4{
                public static final String text = "Very High Frequency (VHF) have __________ wavelengths?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("longer");
                    answersText.add("shorter");
                    answersText.add("longest");
                    answersText.add("shortest");
                }
            }

            public static final class Q5{
                public static final String text = "The density of water is __________?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("none of these");
                    answersText.add("1.5 g/cm3");
                    answersText.add("2 g/cm3");
                    answersText.add("1 g/cm3");
                }
            }

        }
    }

    public static final class Sport {

        public static final String text = "sport";

        public static final class Easy {

            public static final String text = "easy";

            public static final class Q1 {
                public static final String text = "Who won the first world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Germany");
                    answersText.add("USA");
                    answersText.add("Brazil");
                    answersText.add("Uruguay");
                }
            }

            public static final class Q2 {
                public static final String text = "What country hosted the first world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("Uruguay");
                    answersText.add("Russia");
                    answersText.add("Brazil");
                    answersText.add("USA");
                }
            }

            public static final class Q3{
                public static final String text = "Which country has won the most world cups?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Argentina");
                    answersText.add("France");
                    answersText.add("Germany");
                    answersText.add("Brazil");
                }
            }

            public static final class Q4{
                public static final String text = "Which player has scored the most world cup goals?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Cristiano Ronaldo");
                    answersText.add("Pele");
                    answersText.add("Miroslav Klose");
                    answersText.add("Lionel Messi");
                }
            }

            public static final class Q5{
                public static final String text = "Who won the 2010 World Cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("France");
                    answersText.add("Spain");
                    answersText.add("England");
                    answersText.add("Brazil");
                }
            }
        }

        public static final class Moderate {

            public static final String text = "moderate";

            public static final class Q1 {
                public static final String text = "Who was awarded golden boot of the 2014 world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Lineker");
                    answersText.add("Miroslav Klose");
                    answersText.add("Ronaldo");
                    answersText.add("James Rodriguez");
                }
            }

            public static final class Q2 {
                public static final String text = "Which GoalKeeper was awarded golden glove at the 2014 world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Matthew Ryan");
                    answersText.add("Cristiano Ronaldo");
                    answersText.add("Joe Hart");
                    answersText.add("Manuel Neuer");
                }
            }

            public static final class Q3{
                public static final String text = "Which player other than Pele is the youngest player to play a world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Kylian Mbappe");
                    answersText.add("Saloman Olembe");
                    answersText.add("Didier Drogba");
                    answersText.add("Paul Pogba");
                }
            }

            public static final class Q4{
                public static final String text = "Who is the oldest player to ever play a world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Pele");
                    answersText.add("Faryd Mondragon");
                    answersText.add("Lineker");
                    answersText.add("Maldini");
                }
            }

            public static final class Q5{
                public static final String text = "Which player has attended the most world cups?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Pele");
                    answersText.add("Maradona");
                    answersText.add("Buffon");
                    answersText.add("Lothar Matthaus");
                }
            }

        }

        public static final class Tough {

            public static final String text = "tough";

            public static final class Q1 {
                public static final String text = "Which team came second in the world cup 2014?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Sweden");
                    answersText.add("Brazil");
                    answersText.add("Argentina");
                    answersText.add("Australia");
                }
            }

            public static final class Q2{
                public static final String text = "Who came second in the 2010 world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("England");
                    answersText.add("Portugal");
                    answersText.add("Netherlands");
                    answersText.add("Argentina");
                }
            }

            public static final class Q3{
                public static final String text = "Who was Germany's manager in the 2014 world cup?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("Joachim Low");
                    answersText.add("Berti Vogts");
                    answersText.add("Albert Einstein");
                    answersText.add("Leonardo Dicaprio");
                }
            }

            public static final class Q4{
                public static final String text = "Who scored the winning goal in the world cup final 2014?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Thomas Muller");
                    answersText.add("Manuel Neuer");
                    answersText.add("Mario Gotze");
                    answersText.add("Miroslav Klose");
                }
            }

            public static final class Q5{
                public static final String text = "Which team came second in the world cup 2014?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Sweden");
                    answersText.add("Brazil");
                    answersText.add("Argentina");
                    answersText.add("Australia");
                }
            }
        }

    }

    public static final class Technology {

        public static final String text = "technology";

        public static final class Easy {
            public static final String text = "easy";

            public static final class Q1 {
                public static final String text = "How many bits make a byte?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("16 bits");
                    answersText.add("8 bits");
                    answersText.add("24 bits");
                    answersText.add("12 bits");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the meaning of (CPU)?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("Central Processing Unit");
                    answersText.add("Critical Processing Unit");
                    answersText.add("Crucial Processing Unit");
                    answersText.add("Central Printing Unit");
                }
            }

            public static final class Q3{
                public static final String text = "The process of starting or restarting a computer is called:";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Start up point");
                    answersText.add("Booting");
                    answersText.add("Connecting");
                    answersText.add("Resetting");
                }
            }

            public static final class Q4{
                public static final String text = "The other name for a Hard disk is:";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Compact Disc");
                    answersText.add("Fixed Disk");
                    answersText.add("Hard Drive Disk");
                    answersText.add("Floppy Disk");
                }
            }

            public static final class Q5{
                public static final String text = "Which of the items is an input device?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Computer Monitor");
                    answersText.add("Keyboard");
                    answersText.add("Display Board");
                    answersText.add("Overhead Projector");
                }
            }
        }

        public static final class Moderate {
            public static final String text = "moderate";

            public static final class Q1 {
                public static final String text = "What is ISP and what is their functions?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Intenal Service Provider. A company which provide internet connection for a fee.");
                    answersText.add("Internet Service Provider. A company which provide internal transferring data service to an organization.");
                    answersText.add("Internet Service Provider. A company which provide internet connection to other people for a fee.");
                    answersText.add("Internal Service Provider. A company provide internal connection to transfer data between two company.");
                }
            }

            public static final class Q2 {
                public static final String text = "HTTP stand for ______.";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("Hyper Transport Text Protocol");
                    answersText.add("High Text Transport Protocol");
                    answersText.add("High Transport Text Protocol");
                    answersText.add("Hyper Text Transport Protocol");
                }
            }

            public static final class Q3{
                public static final String text = "Arrange the slower to the higher access latency.";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("Magnetic tape, Magnetic disk, Main memory, Cache, Registers.");
                    answersText.add("Registers, Cache, Main memory, Magnetic disk, Magnetic tape.");
                    answersText.add("Registers, Main memory, Cache, Magnetic disk, Magnetic tape.");
                    answersText.add("Magnetic tape, Main memory, Magnetic tape, Cache, Registers.");
                }
            }

            public static final class Q4{
                public static final String text = "The smallet measurement of memory is ______________";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 4;

                static {
                    answersText.add("MB");
                    answersText.add("GB");
                    answersText.add("KB");
                    answersText.add("Bit");
                }
            }

            public static final class Q5{
                public static final String text = "1 GB=____________KB";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("1024");
                    answersText.add("1344");
                    answersText.add("1624");
                    answersText.add("1111");
                }
            }

        }

        public static final class Tough {

            public static final String text="tough";
            public static final class Q1 {
                public static final String text = "What is the protocol used for the majority of network/internet traffic?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 1;

                static {
                    answersText.add("TCP/IP");
                    answersText.add("IPX/SPX");
                    answersText.add("OSX");
                    answersText.add("64-Bit");
                }
            }

            public static final class Q2{
                public static final String text = "What is the most popular wired network topology?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("Bus");
                    answersText.add("Star");
                    answersText.add("Mesh");
                    answersText.add("Ring");
                }
            }

            public static final class Q3{
                public static final String text = "Which file extension is an image file?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("MPG");
                    answersText.add("JPG");
                    answersText.add("MA4");
                    answersText.add("MOV");
                }
            }

            public static final class Q4{
                public static final String text = "What file is a Word 2007 or newer document?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add(".xls");
                    answersText.add(".doc");
                    answersText.add(".docx");
                    answersText.add(".rtf");
                }
            }

            public static final class Q5{
                public static final String text = "If you wanted to add a user to a Domain where would you add them?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 3;

                static {
                    answersText.add("Domain List");
                    answersText.add("Registry");
                    answersText.add("Active Directory");
                    answersText.add("Directory");
                }
            }
        }

    }

}
