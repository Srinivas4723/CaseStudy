import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { Routes,Route,RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { AuthorsignupComponent } from './authorsignup/authorsignup.component';
import { AuthorsigninComponent } from './authorsignin/authorsignin.component';
import { CreatebookComponent } from './createbook/createbook.component';
import { UpdatebookComponent } from './updatebook/updatebook.component';
import { SearchbookComponent } from './searchbook/searchbook.component';
import { AuthorhomeComponent } from './authorhome/authorhome.component';
import { ReaderpageComponent } from './readerpage/readerpage.component';
import { GetmybooksComponent } from './getmybooks/getmybooks.component';


const routes: Routes=[
  {path:"authorsignup",component:AuthorsignupComponent},
  {path:"authorsignin",component:AuthorsigninComponent},
  {path:"authorhome",component:AuthorhomeComponent},
  {path:"createbook",component:CreatebookComponent},
  {path:"updatebook",component:UpdatebookComponent},
  {path:"searchbook",component:SearchbookComponent},
  {path:"readerpage",component:ReaderpageComponent},
  {path:"getmybooks",component:GetmybooksComponent},
  
  
  
]
@NgModule({
  declarations: [
    AppComponent,
    AuthorsignupComponent,
    AuthorsigninComponent,
    AuthorsignupComponent,
    AuthorsigninComponent,
    AuthorhomeComponent,
    CreatebookComponent,
    UpdatebookComponent,
    SearchbookComponent,
    ReaderpageComponent,
    GetmybooksComponent
  ],
  imports: [
    BrowserModule,FormsModule,HttpClientModule, RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
