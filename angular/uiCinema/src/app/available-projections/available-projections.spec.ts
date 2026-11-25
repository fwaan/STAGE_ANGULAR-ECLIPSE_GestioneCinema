import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AvailableProjections } from './available-projections';

describe('AvailableProjections', () => {
  let component: AvailableProjections;
  let fixture: ComponentFixture<AvailableProjections>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvailableProjections],
    }).compileComponents();

    fixture = TestBed.createComponent(AvailableProjections);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
