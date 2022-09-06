import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorsigninComponent } from './authorsignin.component';

describe('AuthorsigninComponent', () => {
  let component: AuthorsigninComponent;
  let fixture: ComponentFixture<AuthorsigninComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorsigninComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorsigninComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
