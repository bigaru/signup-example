# Signup Example

## Architecture
* combination of [MVI](https://proandroiddev.com/mvi-a-new-member-of-the-mv-band-6f7f0d23bc8a) and MVVM

MVI
* benefits
    * No state problem, because there is only a single source of truth
    * Unidirectional data flow
* drawbacks
    * more boilerplate code
    * more indirection

## Improvements
* Instead of Edittext for date, it would be better to provide datepicker (https://developer.android.com/guide/topics/ui/controls/pickers)

## Comments
* Jodatime is used in order to validate date. It is very complicated to deal with edge cases such as leap year using regex.
