import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})
export class CreatebookComponent implements OnInit {

  constructor(public userService:UserService,public router:Router) { }
  book={
    title:'title1',
    category:'category1',
    author:""+sessionStorage.getItem("authorName"),
    price:'600',
    publisher:'pub1',
    publisheddate:'date1',
    chapters:'5',
    active:"true",
  }
  
  createbookblankResponse={
    title:'',
    category:'',
    author:'',
    price:'',
    publisher:'',
    publisheddate:'',
    chapters:'',
    active:'',
  }
  createBook(){
    const observable = this.userService.createBook(this.book);
    observable.subscribe((responseBody:any)=>{
    this.createbookblankResponse.title=responseBody.title;
    this.createbookblankResponse.category=responseBody.category;
    this.createbookblankResponse.author=responseBody.author;
    this.createbookblankResponse.publisher=responseBody.publisher;
    this.createbookblankResponse.publisheddate=responseBody.publisheddate;
    this.createbookblankResponse.chapters=responseBody.chapters;
    this.createbookblankResponse.active=responseBody.active;
    this.createbookblankResponse.price=responseBody.price;
    console.log(JSON.stringify(responseBody));
    },
    (error:any)=>{
      console.log(JSON.stringify(error.error));
      if(typeof error.error!=='string'){
        const createbooksuccessMessageContainer:any= document.getElementById("createbooksuccessMessageContainer");
        createbooksuccessMessageContainer.style.display="block";
        const createbookContainer:any=document.getElementById("createbookContainer");
        createbookContainer.style.display="none";
      }
      
    }
    )
  }
  ngOnInit(): void {
    console.log("oninit");
    const createbooksuccessMessageContainer:any= document.getElementById("createbooksuccessMessageContainer");
        createbooksuccessMessageContainer.style.display="none";
        const createbookContainer:any=document.getElementById("createbookContainer");
        createbookContainer.style.display="block";
  }

}


