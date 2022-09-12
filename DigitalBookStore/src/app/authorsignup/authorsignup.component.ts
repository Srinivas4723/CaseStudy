import { identifierModuleUrl } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-authorsignup',
  templateUrl: './authorsignup.component.html',
  styleUrls: ['./authorsignup.component.css']
})
export class AuthorsignupComponent implements OnInit {

  author = {
    authorname: "",
    authoremail:"",
    password:""
    
  }
  signUpblankResponse:any={
    authorname:"",
    authorpassword:"",
    authoremail:"",

  };
  signUpAuthorNameExists:any;
  signUpAuthorEmailExists:any;
  signUpResponse:any;
  users:any[]=[];
  successMessage:any;
  failureMessage:any;
  signupContainerFlag:boolean=true;
    constructor(public userService: UserService,
        public router:Router) { }
  cancelsignup(){
    this.userService.digitalBooksContainerFlag=true;
    this.userService.slideShowFlag=true;
    this.router.navigate(["/"]);
  }
  saveAuthor(){
    const observable= this.userService.saveAuthor(this.author);
    observable.subscribe((responseBody:any)=>{
    this.signUpblankResponse.authorname=responseBody.authorname;
    this.signUpblankResponse.authorpassword=responseBody.password;
    this.signUpblankResponse.authoremail=responseBody.authoremail;
    console.log("R"+responseBody);
    },
    (error:any)=>{
      console.log(JSON.stringify(error.error));
      this.signUpAuthorNameExists="";
      this.signUpAuthorEmailExists="";
      
      console.log("X"+error.error);
      if(typeof error.error==='string'){
        if(error.error.includes("Name")){
          this.signUpAuthorNameExists=error.error;
        }
        if(error.error.includes("Email") ){
          this.signUpAuthorEmailExists=error.error;
        }
        if(error.error.includes("Error") ){
          this.author.authorname="";
          this.author.authoremail="";
          this.author.password="";
          this.failureMessage="SomeThing Went Wrong!!!"
        }
      }
      else{
        this.signupContainerFlag=false;
        this.successMessage=error.error.text;
      }
    });
  }
  ngOnInit(): void {
    if(sessionStorage.getItem("authorId")!==null){
      this.router.navigate(["/authorhome"]);
    }
    else{
      this.userService.digitalBooksContainerFlag=false;
      this.userService.slideShowFlag=false;
    }
  }
}
