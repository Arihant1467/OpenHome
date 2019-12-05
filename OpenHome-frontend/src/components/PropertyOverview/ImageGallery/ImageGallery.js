import React, { Component } from 'react';
import {IMG_RETRIEVE_BASE_URL} from './../../constants.js';
class ImageGallery extends Component {
    
    constructor(props){
        super(props);

        this.state ={
            imgUrls : this.props.photos,
            currentIndex:0
        }
        this.nextButtonHandle = this.nextButtonHandle.bind(this);
        this.previousButtonHandle = this.previousButtonHandle.bind(this);
    }

    nextButtonHandle = (e) =>{
        console.log("in next");
        const len = this.state.imgUrls.length;
        var index = this.state.currentIndex+1;
        if(index>len-1){
            index = 0;
        }
        this.setState({
            currentIndex : index
        });
    }

    previousButtonHandle = (e) =>{
        console.log("in prev");
        const len = this.state.imgUrls.length;
        var index = this.state.currentIndex-1;
        if(index<=0){
            index =len-1;
        }
        this.setState({
            currentIndex : index
        });
        
    }



    render() { 
        
       const imgUrls = this.state.imgUrls;
       const imgStyle ={
           height:this.props.height || "350px",
           objectFit:'cover',
       }

       const { currentIndex } = this.state;
        return ( 
            <div>
                <div className="row justify-content-center" >
                    <div className="col-md-12">
                        <div id="carouselExampleControls" className="carousel slide" data-ride="carousel">
                            <div className="carousel-inner" style={{border:'0.5px solid white',borderRadius:'5px'}}>

                                <div className="carousel-item active">
                                    <img className="d-block w-100" style={imgStyle} src={`${IMG_RETRIEVE_BASE_URL}/image/${imgUrls[currentIndex]}`} alt="First slide" />
                                </div>

                                {/* {
                                    imgUrls.slice(1).map((image, index) => {
                                        return (
                                            <div class="carousel-item">
                                                <img class="d-block w-100" style={imgStyle} src={`${IMG_RETRIEVE_BASE_URL}/image/${image}`} alt="Second slide" />
                                            </div>

                                        );
                                    })
                                } */}

                            </div>
                            {/* <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a> */}

                            <button className="carousel-control-prev" onClick={this.previousButtonHandle}>
                                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span className="sr-only">Previous</span>
                            </button>
                            <button className="carousel-control-next" onClick={this.nextButtonHandle}>
                                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                                <span className="sr-only">Next</span>
                            </button>
                        </div>
                    </div>
                </div>



            </div>
         );
    }
}
 
export default ImageGallery;
