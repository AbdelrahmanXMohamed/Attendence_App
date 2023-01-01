import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationsComponent } from './vacations.component';

describe('VacationsComponent', () => {
  let component: VacationsComponent;
  let fixture: ComponentFixture<VacationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VacationsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VacationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
