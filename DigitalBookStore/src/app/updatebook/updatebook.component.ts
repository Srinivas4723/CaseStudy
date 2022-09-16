import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService,Category } from '../user.service';

@Component({
  selector: 'app-updatebook',
  templateUrl: './updatebook.component.html',
  styleUrls: ['./updatebook.component.css']
})
export class UpdatebookComponent implements OnInit {
  Books:any;
  bookcategory=Object.values(Category).filter(value => typeof value==="string");
  book=this.userService.hastoeditbook;
  hastoeditbook={
    id:'',
    authorid:"",
    title:'',
    category:"",
    author:'',
    price:'',
    publisher:'',
    publisheddate:null,
    chapters:'',
    active:'',
    content:''
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
    active:'',
    content:''
  };
  bookupdateSuccessMessage: any="";
  constructor(public router:Router,public userService:UserService) { }
  cancelupdatebook(){
    this.userService.editbooksuccessContainerFlag=false;
    this.router.navigate(["/authorhome"]);
  }
  updateBook(){
    this.updatebookblankResponse.title="";
    this.updatebookblankResponse.category="";
    this.updatebookblankResponse.publisher="";
    this.updatebookblankResponse.publisheddate="";
    this.updatebookblankResponse.price="";
    this.updatebookblankResponse.chapters="";
    this.updatebookblankResponse.content="";
    if(this.book.title===""){
      this.updatebookblankResponse.title="Book title cannot be blank";
    }
    if(this.book.chapters===null){
          this.updatebookblankResponse.category="Book category cannot be blank";
    }
    if(this.book.publisher===""){
          this.updatebookblankResponse.publisher="Book publisher cannot be blank";
    }
    if(this.book.publisheddate===""){
          this.updatebookblankResponse.publisheddate="Book publisheddatecannot be blank";
    }
    if(this.book.price==="" || this.book.price===null){
          this.updatebookblankResponse.price="Book pricecannot be blank";
    }
    if(this.book.chapters==="" || this.book.chapters===null){
          this.updatebookblankResponse.chapters="Book chapterscannot be blank";
    }
    if(this.book.content===""){
          this.updatebookblankResponse.content="Book contentcannot be blank";
    }
    else{
    this.userService.editbooksuccessContainerFlag=false;
    const observable= this.userService.updateBook(this.book);
    observable.subscribe((responseBody:any)=>{
      console.log("R"+JSON.stringify(responseBody));
       this.updatebookblankResponse=responseBody;
      },
      (error:any)=>{
        console.log("E"+JSON.stringify(error.error));
      if(typeof error.error.text==="string"){
        this.userService.editBookContainerFlag=false;
        this.userService.editbooksuccessContainerFlag=true;
        console.log("E"+JSON.stringify(error.error));
        this.bookupdateSuccessMessage=error.error.text;
      }
    });
  }
  }
  ngOnInit(): void {
    this.userService.editbooksuccessContainerFlag=false;
    const observable=this.userService.getbooksByAuthorID();
    observable.subscribe((responseBody:any)=>{
      console.log(JSON.stringify(responseBody));
      this.Books=JSON.parse(JSON.stringify(responseBody));
    });
  }
}

