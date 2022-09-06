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
    authorname: "abc",
    authoremail:"abc",
    password:"abc"
    
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
    constructor(public userService: UserService,
      public router:Router) { }
  saveAuthor(){
    const observable= this.userService.saveAuthor(this.author);
    observable.subscribe((responseBody:any)=>{
      
      this.signUpblankResponse.authorname=responseBody.authorname;
      this.signUpblankResponse.authorpassword=responseBody.password;
      this.signUpblankResponse.authoremail=responseBody.authoremail;
      
      

    },
    (error:any)=>{
      console.log(JSON.stringify(error.error));
      this.signUpAuthorNameExists="";
      this.signUpAuthorEmailExists="";
      if(typeof error.error==='string'){
        if(error.error.includes("Author Name")){
          this.signUpAuthorNameExists=error.error;
        }
        if(error.error.includes("Email") ){
          this.signUpAuthorEmailExists=error.error;
        }
      }
      else{
        const signupcontainer:any=document.getElementById('signupContainer');
        signupcontainer.style.display="none";
        const signupsuccesscontainer:any=document.getElementById('signupSuccessContainer');
        signupsuccesscontainer.style.display="block";
        this.successMessage=error.error.text;
        
      }
      
      
    })

  }
  ngOnInit(): void {
    const signupsuccesscontainer:any=document.getElementById('signupSuccessContainer');
    signupsuccesscontainer.style.display="none";
    const digitalBooksContainer:any=document.getElementById("digitalBooksContainer");
    digitalBooksContainer.style.display="none";
  }

}
