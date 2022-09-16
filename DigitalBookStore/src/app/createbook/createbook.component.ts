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
  bookCreateSuccessMessage: any;
  constructor(public userService:UserService,public router:Router) { }  
  obj=Object.values(Category).filter(value => typeof value==="string");
  
  book={
    title:"",
    category:"",
    author:""+sessionStorage.getItem("authorName"),
    price:"",
    publisher:"",
    publisheddate:"",
    chapters:"",
    active:Boolean,
    content:""
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
    content:''
  }
  cancelcreatebook(){
    this.router.navigate(["/authorhome"]);
  }
  createBook(){
    this.createbookblankResponse.title="";
    this.createbookblankResponse.category="";
    this.createbookblankResponse.publisher="";
    this.createbookblankResponse.publisheddate="";
    this.createbookblankResponse.price="";
    this.createbookblankResponse.chapters="";
    this.createbookblankResponse.content="";
    if(this.book.title===""){
      this.createbookblankResponse.title="Book titlecannot be blank";
    }
    if(this.book.category==="" || this.book.category===null){
          this.createbookblankResponse.category="Book categorycannot be blank";
    }
    if(this.book.publisher===""){
          this.createbookblankResponse.publisher="Book publishercannot be blank";
    }
    if(this.book.publisheddate===""){
          this.createbookblankResponse.publisheddate="Book publisheddatecannot be blank";
    }
    if(this.book.price==="" || this.book.price===null){
          this.createbookblankResponse.price="Book pricecannot be blank";
    }
    if(this.book.chapters==="" || this.book.chapters===null){
          this.createbookblankResponse.chapters="Book chapterscannot be blank";
    }
    if(this.book.content===""){
          this.createbookblankResponse.content="Book contentcannot be blank";
    }
    else{
        const observable = this.userService.createBook(this.book);
      observable.subscribe((responseBody:any)=>{
        this.createbookblankResponse=responseBody;
       },
      (error:any)=>{
        console.log("E"+error.error.status+JSON.stringify(error));
        if(error.status===406){
          this.createbookblankResponse.category="Category Cannot be Blank";
        }
        if(typeof error.error!=='string' && error.error.status!==500 ){
          this.userService.createBookContainerFlag=false;
          this.bookCreateSuccessMessage=error.error.text;
          this.userService.createbooksuccessContainerFlag=true;
          this.book={
            title:"",
            category:"",
            author:""+sessionStorage.getItem("authorName"),
            price:"",
            publisher:"",
            publisheddate:"",
            chapters:"",
            active:Boolean,
            content:""
        }
      }
      else{
        this.book={
          title:"",
          category:"",
          author:""+sessionStorage.getItem("authorName"),
          price:"",
          publisher:"",
          publisheddate:"",
          chapters:"",
          active:Boolean,
          content:""};
          this.createbookFailureMessage="OOPS !!! Something Went Wrong";
      }
    });
    }
  }
  ngOnInit(): void {
    this.userService.digitalBooksContainerFlag=false;
    this.userService.slideShowFlag=false;
    this.userService.createBookContainerFlag=true;
    this.userService.createbooksuccessContainerFlag=false;
  }
}


