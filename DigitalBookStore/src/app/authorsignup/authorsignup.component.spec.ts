import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorsignupComponent } from './authorsignup.component';

describe('AuthorsignupComponent', () => {
  let component: AuthorsignupComponent;
  let fixture: ComponentFixture<AuthorsignupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorsignupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorsignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
