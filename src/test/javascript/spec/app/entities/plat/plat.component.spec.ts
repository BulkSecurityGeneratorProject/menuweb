/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MenuwebTestModule } from '../../../test.module';
import { PlatComponent } from '../../../../../../main/webapp/app/entities/plat/plat.component';
import { PlatService } from '../../../../../../main/webapp/app/entities/plat/plat.service';
import { Plat } from '../../../../../../main/webapp/app/entities/plat/plat.model';

describe('Component Tests', () => {

    describe('Plat Management Component', () => {
        let comp: PlatComponent;
        let fixture: ComponentFixture<PlatComponent>;
        let service: PlatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MenuwebTestModule],
                declarations: [PlatComponent],
                providers: [
                    PlatService
                ]
            })
            .overrideTemplate(PlatComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Plat(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.plats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
