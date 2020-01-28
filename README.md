# NY-Times-Most-Popular-Articles
This is a simple news app️ which uses [NY Times API](https://api.nytimes.com) to fetch most popular news headlines from the API.

The codebase focuses 👓 on following key things:
- Object Oriented Programming approach
- Good UI approach e.g. MVC, etc.
- Unit test and code coverage
- Code to be generic and simple
- Leverage today's best coding practices

<img alt="NY Times Most Popular Articles" height="450px" src="https://github.com/JabezMMM/NY-Times-Most-Popular-Articles/blob/master/art/screen.png" />

# Development Setup
You will require latest version of Android Studio 3.0 (or newer) to be able to build the app

## API Key
You'll need to provide API key to fetch the news. Currently the news is fetched from [NY Times](https://api.nytimes.com)

- Generate an API key from [NY Times Developers](https://developer.nytimes.com/get-started)
- Replace the API key in `credentials.properties` in project root folder, as shown below [Make sure to keep the double quotes]:
```
    NEWS_API_KEY = "<YOUR_API_KEY>"
```
- Build the app

## License
The MIT License (MIT)

Copyright (c) 2020 JabezMMM

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
