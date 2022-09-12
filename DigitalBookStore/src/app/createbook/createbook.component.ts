import { DatePipe } from '@angular/common';
import { compileClassMetadata } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService, Category } from '../user.service';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})

export class CreatebookComponent implements OnInit {
  createbookFailureMessage: string="";
  constructor(public userService:UserService,public router:Router) { }  
  obj=Object.values(Category).filter(value => typeof value==="string");
  book={
    title:"",
    category:Category,
    author:""+sessionStorage.getItem("authorName"),
    price:Number,
    publisher:"",
    publisheddate:DatePipe,
    chapters:Number,
    active:Boolean,
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
  cancelcreatebook(){
    this.router.navigate(["/authorhome"]);
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
      console.log("E"+error.error.status+JSON.stringify(error.error));
      if(typeof error.error!=='string' && error.error.status!==500 ){
        this.userService.createBookContainerFlag=false;
        this.userService.createbooksuccessContainerFlag=true;
        this.book={title:"",
        category:Category,
        author:""+sessionStorage.getItem("authorName"),
        price:Number,
        publisher:"",
        publisheddate:DatePipe,
        chapters:Number,
        active:Boolean,}
      }
      else{
        this.book={title:"",
        category:Category,
        author:""+sessionStorage.getItem("authorName"),
        price:Number,
        publisher:"",
        publisheddate:DatePipe,
        chapters:Number,
        active:Boolean,};
        this.createbookFailureMessage="OOPS !!! Something Went Wrong";
      }
    });
  }
  ngOnInit(): void {
    this.userService.digitalBooksContainerFlag=false;
    this.userService.slideShowFlag=false;
    this.userService.createBookContainerFlag=true;
    this.userService.createbooksuccessContainerFlag=false;
  }
}


