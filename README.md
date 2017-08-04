# Pre-work - *Android prework for codepath bootcamp*

**Android prework for codepath bootcam** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Bxr556**

Time spent: **2** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [X] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [ ] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough 

Here's a walkthrough of implemented user stories:



<img src='http://i.imgur.com/cLYSjcH.gif' title='First App for bootcamp' width='' alt='First App for bootcamp' />




GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I enjoy my learning with Android app development platform. It has many design patterns incorporated. For example, Adapter pattern is a very thoughthrough pattern that is very flexible. Mobile has a lot of limitations, such as limited battery life, screen size, computation power etc. In order to overcome all those limitations, a good understandign of the platform is very important. In terms of layout, there are so many different mobile screen sizes out there and in order to have an app that looks good on differnt screen, a good understandign of different type of layout, responsive design etc is critical.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:**  ArrayAdapter is a type of the adapter that implements Adapter interface. It acts as a link between a data set and an adapter view. It is responsible to retrieve data from the data source and use it to populate any view that is related to it. It is very important because it can display a large data sets very efficiently. 

ConvertView is used to increase the performance of the adapter. It is recycled when adapter need to display new item and the existing view item is not longer needed. Instead of creating a new view item, an existing view item is reused. 


## Notes

Describe any challenges encountered while building the app.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
