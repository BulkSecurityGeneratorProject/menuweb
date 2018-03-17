/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MenuwebTestModule } from '../../../test.module';
import { PlatDetailComponent } from '../../../../../../main/webapp/app/entities/plat/plat-detail.component';
import { PlatService } from '../../../../../../main/webapp/app/entities/plat/plat.service';
import { Plat } from '../../../../../../main/webapp/app/entities/plat/plat.model';

describe('Component Tests', () => {

    describe('Plat Management Detail Component', () => {
        let comp: PlatDetailComponent;
        let fixture: ComponentFixture<PlatDetailComponent>;
        let service: PlatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MenuwebTestModule],
                declarations: [PlatDetailComponent],
                providers: [
                    PlatService
                ]
            })
            .overrideTemplate(PlatDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Plat(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.plat).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
