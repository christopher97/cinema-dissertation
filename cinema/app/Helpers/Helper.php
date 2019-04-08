<?php

// app/helpers/Helper.php
namespace App\Helpers;

use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;
use Symfony\Component\Process\Process;
use Symfony\Component\Process\Exception\ProcessFailedException;

class Helper {

    public static function processRequest(string $url, string $method, $json) {
        $client = new Client;
        try {
            if ($json != null) {
                $response = $client->request($method, $url, [
                    'json' => $json
                ]);
            } else {
                $response = $client->request($method, $url);
            }

            $code = $response->getStatusCode();
            $body = json_decode((string)$response->getBody(), true);

            return response()->json($body, $code);
        } catch(GuzzleException $e) {
            $code = $e->getCode();
            return response()->json(['error' => 'Invalid Request'], $code);
        }
    }

    public static function objectToArray($obj) {
        $arr = array();
        $n = count($obj);
        for($i=0; $i<$n; $i++) {
            array_push($arr, $obj[$i]->name);
        }

        return $arr;
    }

    public static function processArray($content, $arr) {
        $n = count($arr);
        for($i=0; $i<$n; $i++) {
            $str = Helper::processString($arr[$i]);
            // if not empty, put space
            if (!($content === '')) {
                $content .= " ";
            }
            $content .= $str;
        }

        return $content;
    }

    public static function processString($str) {
        $result = preg_replace('/\s*/', '', $str);
        $result = strtolower($result);

        return $result;
    }

}