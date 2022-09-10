import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-updatebook',
  templateUrl: './updatebook.component.html',
  styleUrls: ['./updatebook.component.css']
})
export class UpdatebookComponent implements OnInit {
  Books:any;
  book:any=this.userService.hastoeditbook;
  hastoeditbook={
    id:'',
    authorid:"",
    title:'',
    category:'',
    author:'',
    price:'',
    publisher:'',
    publisheddate:'',
    chapters:'',
    active:'',
  };
  nobookFoundMessage: any;
  updatebookblankResponse={
    title:'',
    category:'',
    author:'',
    price:'',
    publisher:'',
    publisheddate:'',
    chapters:'',
    active:''
  };
  bookupdateSuccessMessage: any="";
  constructor(public router:Router,public userService:UserService) { }
  cancelupdatebook(){
    this.router.navigate(["/authorhome"]);
  }
  updateBook(){
    const observable= this.userService.updateBook(this.book);
    observable.subscribe((responseBody:any)=>{
      console.log("R"+JSON.stringify(responseBody));
      this.updatebookblankResponse.title=responseBody.title;
    this.updatebookblankResponse.category=responseBody.category;
    this.updatebookblankResponse.author=responseBody.author;
    this.updatebookblankResponse.publisher=responseBody.publisher;
    this.updatebookblankResponse.publisheddate=responseBody.publisheddate;
    this.updatebookblankResponse.chapters=responseBody.chapters;
    this.updatebookblankResponse.active=responseBody.active;
    this.updatebookblankResponse.price=responseBody.price;
    },
    (error:any)=>{
      console.log("E"+JSON.stringify(error.error));
     if(typeof error.error.text==="string"){
      // const authorBookContainer:any = document.getElementById("authorBookContainer");
      // authorBookContainer.style.display="block";
      // const editBookContainer:any=document.getElementById("editBookContainer");
      // editBookContainer.style.display="none";
      this.userService.editBookContainerFlag=false;
      this.userService.editbooksuccessContainerFlag=true;
      console.log("E"+JSON.stringify(error.error));
       this.bookupdateSuccessMessage=error.error.text;
     }
    })
  }
  ngOnInit(): void {
    const observable=this.userService.getbooksByAuthorID();
    observable.subscribe((responseBody:any)=>{
      console.log(JSON.stringify(responseBody));
      this.Books=JSON.parse(JSON.stringify(responseBody));
    },
    (error:any)=>{
      
    })
  }
  }

