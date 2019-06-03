

Android LiveData


LiveData is a part of the architecture patterns. It’s basically a data holder that contains primitive/collection types.
It’s used for observing changes in the view and updating the view when it is ACTIVE. Thus, LiveData is lifecycle aware.

We know that ViewModels are used to communicate the data to the View. Using ViewModels alone can be a tedious and costly
operation since we need to make multiple calls each time the data has to alter the View. Plus we need to store the data Model at different places.

LiveData is based on the Observer Pattern and makes the communication between the ViewModel and View easy.

It observes for data changes and updates the data automatically instead of us doing multiple calls in adding and deleting data
references from multiple places (for example SQLite, ArrayList, ViewModel).

