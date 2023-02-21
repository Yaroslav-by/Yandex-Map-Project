# Yandex-Map-Project
<a name="readme-top"></a>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/Yaroslav-by/Yandex-Map-Project">
    <img src="https://avatars.mds.yandex.net/get-lpc/1368426/d673719c-7745-4600-9732-9af2355bf902/width_480_q70" alt="Logo" width="140" height="80">
  </a>

<h3 align="center">Yandex Map API Project</h3>

  <p align="center">
    My simple implementation of Yandex Maps by using Yandex API
    <br />
    <br />
    <a href="https://github.com/Yaroslav-by/Yandex-Map-Project">View Demo</a>
    ·
    <a href="https://github.com/Yaroslav-by/Yandex-Map-Project/issues">Report Bug</a>
    ·
    <a href="https://github.com/Yaroslav-by/Yandex-Map-Project/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[<a href="https://ibb.co/9t9QMhc"><img src="https://i.ibb.co/ySnH3pd/image.png" alt="image" border="0" /></a>](https://github.com/Yaroslav-by/Yandex-Map-Project)

This project uses Yandex api (Yandex StaticMaps, Geocoder and Geosearch).
<br> <br>
A get request is formed in the search bar, after pressing the "search" button, this request is sent to the Yandex server and we receive a JSON Response.
All the necessary information is obtained from it: coordinates, name, address, telephones, working hours, etc. <br>
MapObject.class is responsible for get requests. Also for processing json-responses and obtaining the necessary information from them.
<br>


<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* Java
* Yandex StaticMaps
* Yandex Geocoder
* Yandex Geosearch

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

### Installation

1. Get a free API Key at [https://developer.tech.yandex.ru/](https://developer.tech.yandex.ru/)
2. Clone the repo
   ```sh
   git clone https://github.com/Yaroslav-by/Yandex-Map-Project
   ```
3. Create your yandexAPI in `yandexAPI`
   ```java
   package yandexAPI;
      public abstract class Apikey {

	       private static final String APIKEY = {ENTER YOUR API KEY HERE};
	
	       protected static String getAPIKEY() {
		          return APIKEY;
	       }
	
   }
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage
In the "Search bar", we write the query we need
<br>
<a href="https://ibb.co/r5xzBTt"><img src="https://i.ibb.co/NW9J0Q3/2.png" alt="2" border="0" /></a>
<br>
<br>

In the "Search Results" field, we can see what Yandex sent us at our request.
<br>
<a href="https://ibb.co/dk4KVK0"><img src="https://i.ibb.co/SVwvMvX/image.png" alt="image" border="0" /></a>
<br>
<br>

In the "Information" field, we can see all the information that Yandex sent us on this object.
<br>
<a href="https://ibb.co/gSr0059"><img src="https://i.ibb.co/GWdZZKp/image.png" alt="image" border="0" /></a>
<br>
<br>

There is also a map with a zoom scale and the ability to switch between "satellite" and "diagram" views. There is also an option to turn on the display of traffic jams.
<br>
<a href="https://ibb.co/hZw9thN"><img src="https://i.ibb.co/CMD8TNy/image.png" alt="image" border="0" /></a>
<br>
<br>
<a href="https://ibb.co/0jRxk22"><img src="https://i.ibb.co/MDxmbSS/image.png" alt="image" border="0" /></a>
<br>
<br>
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [+] Simple implementation
- [-] Make it much more like the original one
- [-] Make it with Spring or Spring Boot framework

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Yaroslav Salnikov - asgen-yaroslav@mail.ru

Project Link: [Yandex Map API Project](https://github.com/Yaroslav-by/Yandex-Map-Project)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



